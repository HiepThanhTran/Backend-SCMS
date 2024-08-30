package com.fh.scms.repository.implement;

import com.fh.scms.dto.statistics.InventoryStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatusReportEntry;
import com.fh.scms.dto.statistics.RevenueStatisticsEntry;
import com.fh.scms.dto.statistics.WarehouseStatusReportEntry;
import com.fh.scms.enums.OrderType;
import com.fh.scms.enums.ProductStatus;
import com.fh.scms.pojo.Order;
import com.fh.scms.pojo.*;
import com.fh.scms.repository._StatisticsRepository;
import com.fh.scms.util.Constants;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Repository
@Transactional
public class _StatisticsRepositoryImplement implements _StatisticsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public RevenueStatisticsEntry generateStatisticsRevenueByWeeks(int days) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<RevenueStatisticsEntry> criteria = builder.createQuery(RevenueStatisticsEntry.class);

        Root<Invoice> invoiceRoot = criteria.from(Invoice.class);

        // Subquery lấy doanh thu của mỗi hóa đơn
        Subquery<BigDecimal> subqueryAmount = criteria.subquery(BigDecimal.class);
        Root<Invoice> subqueryRootAmount = subqueryAmount.from(Invoice.class);

        subqueryAmount.select(subqueryRootAmount.get("totalAmount"));
        subqueryAmount.where(builder.equal(subqueryRootAmount.get("id"), invoiceRoot.get("id")));
        subqueryAmount.groupBy(subqueryRootAmount.get("id"));

        // Subquery để tính tổng đơn hàng của mỗi hóa đơn
        Subquery<Long> subqueryOrders = criteria.subquery(Long.class);
        Root<Invoice> subqueryRootOrders = subqueryOrders.from(Invoice.class);
        Join<Invoice, Order> subqueryOrderJoinOrders = subqueryRootOrders.join("order");

        subqueryOrders.select(builder.countDistinct(subqueryOrderJoinOrders.get("id")));
        subqueryOrders.where(builder.equal(subqueryRootOrders.get("id"), invoiceRoot.get("id")));

        // Subquery để tính tổng sản phẩm bán được của mỗi hóa đơn
        Subquery<Long> subqueryProducts = criteria.subquery(Long.class);
        Root<Invoice> subqueryRootProducts = subqueryProducts.from(Invoice.class);
        Join<Invoice, Order> subqueryOrderJoinProducts = subqueryRootProducts.join("order");
        Join<Order, OrderDetails> subqueryOrderDetailsJoinProducts = subqueryOrderJoinProducts.join("orderDetailsSet");

        subqueryProducts.select(builder.countDistinct(subqueryOrderDetailsJoinProducts.get("product").get("id")));
        subqueryProducts.where(builder.equal(subqueryRootProducts.get("id"), invoiceRoot.get("id")));

        // Tính toán tổng doanh thu, tổng đơn hàng và tổng sản phẩm bán được của toàn bộ hóa đơn
        criteria.multiselect(
                builder.coalesce(builder.sum(subqueryAmount), BigDecimal.ZERO),
                builder.coalesce(builder.sum(subqueryOrders), 0L),
                builder.coalesce(builder.sum(subqueryProducts), 0L)
        );

        // Tính toán ngày thứ hai và chủ nhật của tuần chứa ngày hiện tại (now)
        // Nếu days = 0 thì lấy tuần hiện tại
        LocalDate today = LocalDate.now().minusDays(days);
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        LocalDate sunday = monday.plusDays(6);
        Date startDateTime = Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(sunday.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        // Chỉ lấy các hóa đơn đã thanh toán, loại hóa đơn là xuất kho và nằm trong tuần hiện tại
        criteria.where(
                builder.equal(invoiceRoot.get("paid"), true),
                builder.equal(invoiceRoot.get("order").get("type"), OrderType.OUTBOUND),
                builder.between(invoiceRoot.get("createdAt"), startDateTime, endDateTime)
        );

        // Lấy kết quả tổng quan (doanh thu, đơn hàng, sản phẩm)
        RevenueStatisticsEntry result = session.createQuery(criteria).getSingleResult();

        // Tính toán chi tiết doanh thu theo ngày trong tuần bằng cách nhóm theo DAYOFWEEK
        CriteriaQuery<Object[]> dayQuery = builder.createQuery(Object[].class);
        Root<Invoice> dayRoot = dayQuery.from(Invoice.class);

        dayQuery.multiselect(
                builder.function("DAYOFWEEK", Integer.class, dayRoot.get("createdAt")),
                builder.sum(dayRoot.get("totalAmount"))
        );
        dayQuery.where(
                builder.equal(dayRoot.get("paid"), true),
                builder.equal(dayRoot.get("order").get("type"), OrderType.OUTBOUND),
                builder.between(dayRoot.get("createdAt"), startDateTime, endDateTime)
        );
        dayQuery.groupBy(builder.function("DAYOFWEEK", Integer.class, dayRoot.get("createdAt")));
        dayQuery.orderBy(builder.asc(builder.function("DAYOFWEEK", Integer.class, dayRoot.get("createdAt"))));

        List<Object[]> dailyResults = session.createQuery(dayQuery).getResultList();

        // Mapping dữ liệu kết quả vào RevenueStatisticsEntry.DailyDetail
        List<RevenueStatisticsEntry.DailyDetail> details = new ArrayList<>();
        Map<Integer, BigDecimal> dayAmountMap = new HashMap<>();
        for (Object[] row : dailyResults) {
            int dayOfWeek = Integer.parseInt(String.valueOf(row[0]));
            int adjustedDay = (dayOfWeek == 1) ? 8 : dayOfWeek;
            BigDecimal amount = new BigDecimal(String.valueOf(row[1]));
            dayAmountMap.put(adjustedDay, amount);
        }

        // Gán dữ liệu cho thứ 2 -> Chủ nhật
        for (int i = 2; i <= 8; i++) {
            BigDecimal amount = dayAmountMap.getOrDefault(i, BigDecimal.ZERO);
            details.add(new RevenueStatisticsEntry.DailyDetail(i, amount));
        }

        result.setDetails(details);
        return result;
    }

    @Override
    public List<Object[]> generateSupplierPerformanceReport(Long supplierId, Integer year) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Rating> ratingRoot = criteria.from(Rating.class);
        Join<Rating, Supplier> supplierJoin = ratingRoot.join("supplier");

        // Tính trung bình điểm số theo tháng
        criteria.multiselect(
                ratingRoot.get("criteria"),
                builder.function("MONTH", Integer.class, ratingRoot.get("createdAt")),
                builder.avg(ratingRoot.get("rating"))
        );
        criteria.where(
                builder.equal(supplierJoin.get("id"), supplierId),
                builder.equal(builder.function("YEAR", Integer.class, ratingRoot.get("createdAt")), year)
        );
        criteria.groupBy(
                supplierJoin.get("id"),
                ratingRoot.get("criteria"),
                builder.function("MONTH", Integer.class, ratingRoot.get("createdAt"))
        );
        criteria.orderBy(builder.asc(builder.function("MONTH", Integer.class, ratingRoot.get("createdAt"))));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<WarehouseStatusReportEntry> generateWarehouseStatusReport() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseStatusReportEntry> criteria = builder.createQuery(WarehouseStatusReportEntry.class);

        Root<Warehouse> warehouseRoot = criteria.from(Warehouse.class);
        Join<Warehouse, Inventory> inventoryJoin = warehouseRoot.join("inventorySet", JoinType.LEFT);
        Join<Inventory, InventoryDetails> inventoryDetailsJoin = inventoryJoin.join("inventoryDetailsSet", JoinType.LEFT);

        // Tính tổng số lượng hàng tồn kho và dung lượng còn lại
        // Nếu tổng số lượng hàng tồn kho rỗng (Tức là kho chưa có hàng tồn kho nào) thì trả về dung lượng của kho
        // Ngược lại trả về dung lượng còn lại của kho (Dung lượng - Tổng số lượng hàng tồn kho)
        Expression<Number> totalQuantity = builder.sum(inventoryDetailsJoin.get("quantity"));
        Expression<Number> remainingCapacity = builder.<Number>selectCase()
                .when(totalQuantity.isNotNull(), builder.diff(warehouseRoot.get("capacity"), totalQuantity))
                .otherwise(warehouseRoot.get("capacity"));

        // Chọn các trường cần thiết
        // Parse kết quả trả về thành WarehouseReportEntry
        criteria.select(builder.construct(
                WarehouseStatusReportEntry.class,
                warehouseRoot.get("id"),
                warehouseRoot.get("name"),
                warehouseRoot.get("capacity"),
                remainingCapacity
        ));
        // Group by theo id, tên và dung lượng của kho
        criteria.groupBy(warehouseRoot.get("id"), warehouseRoot.get("name"), warehouseRoot.get("capacity"));

        Query<WarehouseStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<InventoryStatusReportEntry> generateInventoryStatusReportOfWarehouse(Long warehouseId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryStatusReportEntry> criteria = builder.createQuery(InventoryStatusReportEntry.class);

        Root<Inventory> inventoryRoot = criteria.from(Inventory.class);
        Join<Inventory, InventoryDetails> inventoryDetailsJoin = inventoryRoot.join("inventoryDetailsSet");

        // Nếu tổng số lượng hàng tồn kho rỗng thì trả về 0
        // Parse kết quả trả về thành InventoryReportEntry
        criteria.select(builder.construct(
                InventoryStatusReportEntry.class,
                inventoryRoot.get("id"),
                inventoryRoot.get("name"),
                builder.coalesce(builder.sum(inventoryDetailsJoin.get("quantity")), 0L)
        ));
        criteria.where(builder.equal(inventoryRoot.get("warehouse").get("id"), warehouseId));
        criteria.groupBy(inventoryRoot.get("id"));

        Query<InventoryStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> generateStatisticsProductsStatusOfInventory(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        LocalDate today = LocalDate.now();
        LocalDate plusDays = today.plusDays(Constants.EXPIRING_SOON_DAYS);

        Date currentDate = Date.from(plusDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expiryDate = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        // Tính số lượng sản phẩm còn hạn, đã hết hạn và sắp hết hạn
        // Sản phẩm còn hạn: ExpiryDate >= Ngày hiện tại + Constants.EXPIRING_SOON_DAYS (30 ngày)
        Expression<Number> validCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.greaterThanOrEqualTo(productRoot.get("expiryDate"), expiryDate), 1L).otherwise(0L)
        );

        // Sản phẩm đã hết hạn: ExpiryDate < Ngày hiện tại
        Expression<Number> expiredCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.lessThan(productRoot.get("expiryDate"), currentDate), 1L).otherwise(0L)
        );

        // Sản phẩm sắp hết hạn: ExpiryDate >= Ngày hiện tại và ExpiryDate <= Ngày hiện tại + Constants.EXPIRING_SOON_DAYS (30 ngày)
        Expression<Number> expiringSoonCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.between(productRoot.get("expiryDate"), currentDate, expiryDate), 1L).otherwise(0L)
        );

        criteria.multiselect(validCount, expiredCount, expiringSoonCount);
        criteria.where(builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<ProductStatusReportEntry> findProductsOfInventoryByStatus(Long inventoryId, @NotNull String status) {
        ProductStatus productStatus;
        try {
            productStatus = ProductStatus.valueOf(status.toUpperCase(Locale.getDefault()));
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }

        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductStatusReportEntry> criteria = builder.createQuery(ProductStatusReportEntry.class);

        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        criteria.select(builder.construct(
                ProductStatusReportEntry.class,
                productRoot.get("id"),
                productRoot.get("name"),
                productRoot.get("unit").get("name"),
                inventoryDetailsJoin.get("quantity"),
                productRoot.get("expiryDate")
        ));

        LocalDate today = LocalDate.now();
        LocalDate plusDays = today.plusDays(Constants.EXPIRING_SOON_DAYS);

        Date currentDate = Date.from(plusDays.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date expiryDate = Date.from(today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

        switch (productStatus) {
            case EXPIRING_SOON:
                // Lọc theo ExpiryDate >= Ngày hiện tại và ExpiryDate <= Ngày hiện tại + Constants.EXPIRING_SOON_DAYS (30 ngày)
                criteria.where(builder.between(productRoot.get("expiryDate"), currentDate, expiryDate),
                        builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
                );
                break;
            case EXPIRED:
                // Lọc theo ExpiryDate < Ngày hiện tại
                criteria.where(
                        builder.lessThanOrEqualTo(productRoot.get("expiryDate"), currentDate),
                        builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
                );
                break;
        }

        Query<ProductStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }
}
