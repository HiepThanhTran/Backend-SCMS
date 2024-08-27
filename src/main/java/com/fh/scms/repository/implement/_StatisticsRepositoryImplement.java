package com.fh.scms.repository.implement;

import com.fh.scms.dto.statistics.InventoryStatusReportEntry;
import com.fh.scms.dto.statistics.ProductStatusReportEntry;
import com.fh.scms.dto.statistics.WarehouseStatusReportEntry;
import com.fh.scms.pojo.*;
import com.fh.scms.repository._StatisticsRepository;
import com.fh.scms.util.Constants;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class _StatisticsRepositoryImplement implements _StatisticsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public BigDecimal generateRevenueByLast24Hours() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteria = builder.createQuery(BigDecimal.class);
        Root<Invoice> invoiceRoot = criteria.from(Invoice.class);

        criteria.select(builder.sum(invoiceRoot.get("totalAmount")));
        // Lọc theo ngày tạo hóa đơn từ thời điểm 24 giờ trước đến thời điểm hiện tại
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        criteria.where(builder.between(invoiceRoot.get("createdAt"), twentyFourHoursAgo, LocalDateTime.now()));

        Query<BigDecimal> query = session.createQuery(criteria);

        return Optional.ofNullable(query.getSingleResult()).orElse(BigDecimal.ZERO);
    }


    @Override
    public List<Object[]> generateSupplierPerformanceReport(Long supplierId, Integer year) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        // Join các bảng Rating và Supplier
        Root<Rating> ratingRoot = criteria.from(Rating.class);
        Join<Rating, Supplier> supplierJoin = ratingRoot.join("supplier");

        // Tính trung bình điểm số theo tháng
        criteria.multiselect(
                supplierJoin.get("id"),
                supplierJoin.get("name"),
                ratingRoot.get("criteria"),
                builder.function("MONTH", Integer.class, ratingRoot.get("createdAt")),
                builder.avg(ratingRoot.get("rating"))
        );
        // Lọc theo id của nhà cung cấp và năm
        criteria.where(builder.equal(supplierJoin.get("id"), supplierId),
                builder.equal(builder.function("YEAR", Integer.class, ratingRoot.get("createdAt")), year));
        // Group by nhà cung cấp, tiêu chí và tháng
        criteria.groupBy(
                supplierJoin.get("id"),
                supplierJoin.get("name"),
                ratingRoot.get("criteria"),
                builder.function("MONTH", Integer.class, ratingRoot.get("createdAt"))
        );
        // Sắp xếp theo tháng tăng dần
        criteria.orderBy(builder.asc(builder.function("MONTH", Integer.class, ratingRoot.get("createdAt"))));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<WarehouseStatusReportEntry> generateWarehouseStatusReport() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<WarehouseStatusReportEntry> criteria = builder.createQuery(WarehouseStatusReportEntry.class);

        // Join các bảng Warehouse, Inventory và InventoryDetails
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
    public List<InventoryStatusReportEntry> generateInventoryStatusReport(Long warehouseId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryStatusReportEntry> criteria = builder.createQuery(InventoryStatusReportEntry.class);

        // Join các bảng Inventory và InventoryDetails
        Root<Inventory> inventoryRoot = criteria.from(Inventory.class);
        Join<Inventory, InventoryDetails> inventoryDetailsJoin = inventoryRoot.join("inventoryDetailsSet");

        // Chọn các trường cần thiết
        // Nếu tổng số lượng hàng tồn kho rỗng thì trả về 0
        // Parse kết quả trả về thành InventoryReportEntry
        criteria.select(builder.construct(
                InventoryStatusReportEntry.class,
                inventoryRoot.get("id"),
                inventoryRoot.get("name"),
                builder.coalesce(builder.sum(inventoryDetailsJoin.get("quantity")), 0L)
        ));
        // Lọc theo id của kho và Group by theo id của hàng tồn kho
        criteria.where(builder.equal(inventoryRoot.get("warehouse").get("id"), warehouseId)).groupBy(inventoryRoot.get("id"));

        Query<InventoryStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> generateStatisticsProductsByExpiryDate(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        // Join các bảng Product và InventoryDetails
        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        // Tính số lượng sản phẩm còn hạn, đã hết hạn và sắp hết hạn
        // Sản phẩm còn hạn: ExpiryDate >= Ngày hiện tại
        Expression<Number> validCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.greaterThanOrEqualTo(
                                productRoot.get("expiryDate"),
                                LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)), 1L)
                        .otherwise(0L)
        );

        // Sản phẩm đã hết hạn: ExpiryDate < Ngày hiện tại
        Expression<Number> expiredCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.lessThan(productRoot.get("expiryDate"), LocalDate.now()), 1L)
                        .otherwise(0L)
        );

        // Sản phẩm sắp hết hạn: ExpiryDate >= Ngày hiện tại và ExpiryDate <= Ngày hiện tại + Constants.EXPIRING_SOON_DAYS (30 ngày)
        Expression<Number> expiringSoonCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.between(
                                productRoot.get("expiryDate"), LocalDate.now(),
                                LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)), 1L)
                        .otherwise(0L)
        );

        // Chọn các trường cần thiết
        criteria.multiselect(validCount, expiredCount, expiringSoonCount);
        // Lọc theo id của hàng tồn kho
        criteria.where(builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<ProductStatusReportEntry> findProductsExpiringSoon(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductStatusReportEntry> criteria = builder.createQuery(ProductStatusReportEntry.class);

        // Join các bảng Product và InventoryDetails
        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        // Chọn các trường cần thiết
        criteria.select(builder.construct(
                ProductStatusReportEntry.class,
                productRoot.get("id"),
                productRoot.get("name"),
                productRoot.get("unit").get("name"),
                inventoryDetailsJoin.get("quantity"),
                productRoot.get("expiryDate")
        ));
        // Lọc theo ExpiryDate >= Ngày hiện tại và ExpiryDate <= Ngày hiện tại + Constants.EXPIRING_SOON_DAYS (30 ngày)
        criteria.where(builder.between(productRoot.get("expiryDate"),
                        LocalDate.now(), LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)),
                builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
        );

        Query<ProductStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<ProductStatusReportEntry> findExpiredProducts(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductStatusReportEntry> criteria = builder.createQuery(ProductStatusReportEntry.class);

        // Join các bảng Product và InventoryDetails
        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        // Chọn các trường cần thiết
        criteria.select(builder.construct(
                ProductStatusReportEntry.class,
                productRoot.get("id"),
                productRoot.get("name"),
                productRoot.get("unit").get("name"),
                inventoryDetailsJoin.get("quantity"),
                productRoot.get("expiryDate")
        ));
        // Lọc theo ExpiryDate < Ngày hiện tại
        criteria.where(
                builder.lessThanOrEqualTo(productRoot.get("expiryDate"), LocalDate.now()),
                builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
        );

        Query<ProductStatusReportEntry> query = session.createQuery(criteria);

        return query.getResultList();
    }
}
