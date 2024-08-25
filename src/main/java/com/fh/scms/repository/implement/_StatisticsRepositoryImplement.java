package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Inventory;
import com.fh.scms.pojo.InventoryDetails;
import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Warehouse;
import com.fh.scms.repository._StatisticsRepository;
import com.fh.scms.util.Constants;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class _StatisticsRepositoryImplement implements _StatisticsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public List<Object[]> getWarehouseReport() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Warehouse> warehouseRoot = criteria.from(Warehouse.class);
        Join<Warehouse, Inventory> inventoryJoin = warehouseRoot.join("inventorySet", JoinType.LEFT);
        Join<Inventory, InventoryDetails> inventoryDetailsJoin = inventoryJoin.join("inventoryDetailsSet", JoinType.LEFT);

        Expression<Number> remainingCapacity = builder.<Number>selectCase()
                .when(builder.sum(inventoryDetailsJoin.get("quantity")).isNotNull(),
                        builder.diff(warehouseRoot.get("capacity"),
                                builder.sum(inventoryDetailsJoin.get("quantity"))))
                .otherwise(warehouseRoot.get("capacity"));

        criteria.multiselect(
                warehouseRoot.get("id"),
                warehouseRoot.get("name"),
                warehouseRoot.get("capacity"),
                remainingCapacity
        );
        criteria.groupBy(
                warehouseRoot.get("id"),
                warehouseRoot.get("name"),
                warehouseRoot.get("capacity")
        );

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> getInventoryReport(Long warehouseId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Inventory> inventoryRoot = criteria.from(Inventory.class);
        Join<Inventory, InventoryDetails> inventoryDetailsJoin = inventoryRoot.join("inventoryDetailsSet");

        criteria.multiselect(
                inventoryRoot.get("id"),
                inventoryRoot.get("name"),
                builder.coalesce(builder.sum(inventoryDetailsJoin.get("quantity")), 0L)
        );
        criteria.where(builder.equal(inventoryRoot.get("warehouse").get("id"), warehouseId));
        criteria.groupBy(inventoryRoot.get("id"));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> statisticsProductsByExpiryDate(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        Expression<Number> validCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.greaterThanOrEqualTo(
                                productRoot.get("expiryDate"),
                                LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)), 1L)
                        .otherwise(0L)
        );

        Expression<Number> expiredCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.lessThan(productRoot.get("expiryDate"), LocalDate.now()), 1L)
                        .otherwise(0L)
        );

        Expression<Number> expiringSoonCount = builder.sum(
                builder.<Number>selectCase()
                        .when(builder.between(
                                productRoot.get("expiryDate"), LocalDate.now(),
                                LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)), 1L)
                        .otherwise(0L)
        );

        criteria.multiselect(
                validCount,
                expiredCount,
                expiringSoonCount
        );
        criteria.where(builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId));

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> findProductsExpiringSoon(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        criteria.multiselect(
                productRoot.get("id"),
                productRoot.get("name"),
                productRoot.get("unit").get("name"),
                inventoryDetailsJoin.get("quantity"),
                productRoot.get("expiryDate")
        );
        criteria.where(
                builder.between(
                        productRoot.get("expiryDate"),
                        LocalDate.now(),
                        LocalDate.now().plusDays(Constants.EXPIRING_SOON_DAYS)),
                builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
        );

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Object[]> findExpiredProducts(Long inventoryId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Product> productRoot = criteria.from(Product.class);
        Join<Product, InventoryDetails> inventoryDetailsJoin = productRoot.join("inventoryDetailsSet");

        criteria.multiselect(
                productRoot.get("id"),
                productRoot.get("name"),
                productRoot.get("unit").get("name"),
                inventoryDetailsJoin.get("quantity"),
                productRoot.get("expiryDate")
        );
        criteria.where(
                builder.lessThanOrEqualTo(productRoot.get("expiryDate"), LocalDate.now()),
                builder.equal(inventoryDetailsJoin.get("inventory").get("id"), inventoryId)
        );

        Query<Object[]> query = session.createQuery(criteria);

        return query.getResultList();
    }
}
