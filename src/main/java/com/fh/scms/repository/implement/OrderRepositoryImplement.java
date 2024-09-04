package com.fh.scms.repository.implement;

import com.fh.scms.dto.order.OrderResponseForTracking;
import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
import com.fh.scms.pojo.Order;
import com.fh.scms.pojo.*;
import com.fh.scms.repository.OrderRepository;
import com.fh.scms.util.Constants;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Repository
@Transactional
public class OrderRepositoryImplement implements OrderRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public List<Order> findRecentOrders() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        criteria.select(root).orderBy(builder.desc(root.get("id")));
        Query<Order> query = session.createQuery(criteria);
        query.setMaxResults(Constants.RECENTLY_ORDERS_NUMBER);

        return query.getResultList();
    }

    @Override
    public List<Order> findByDeliveryScheduleId(Long deliveryScheduleId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        criteria.select(root).where(builder.equal(root.get("deliverySchedule").get("id"), deliveryScheduleId));
        Query<Order> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public Order findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Order.class, id);
    }

    @Override
    public OrderResponseForTracking findByOrderNumber(String orderNumber) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderResponseForTracking> criteria = builder.createQuery(OrderResponseForTracking.class);

        Root<Order> root = criteria.from(Order.class);
        Join<Order, DeliverySchedule> deliveryScheduleJoin = root.join("deliverySchedule");
        Join<DeliverySchedule, Shipment> shipmentJoin = deliveryScheduleJoin.join("shipmentSet");

        try {
            criteria.select(builder.construct(
                    OrderResponseForTracking.class,
                    root.get("type"),
                    root.get("status"),
                    root.get("createdAt"),
                    deliveryScheduleJoin.get("scheduledDate"),
                    shipmentJoin.get("cost"),
                    shipmentJoin.get("currentLocation"),
                    shipmentJoin.get("trackingNumber"),
                    shipmentJoin.get("status"),
                    shipmentJoin.get("shipper").get("name")
            )).where(builder.equal(root.get("orderNumber"), orderNumber));

            Query<OrderResponseForTracking> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (Exception e) {
            LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error occurred while getting order by order number", e);
            return null;
        }
    }

    @Override
    public void save(Order order) {
        Session session = this.getCurrentSession();
        session.persist(order);
    }

    @Override
    public void update(Order order) {
        Session session = this.getCurrentSession();
        session.merge(order);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Order order = session.get(Order.class, id);
        session.delete(order);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Order> root = criteria.from(Order.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Order> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
        Root<Order> root = criteria.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("type", "status", "user", "invoice").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "type":
                            try {
                                OrderType type = OrderType.valueOf(params.get("type").toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("type"), type));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderType Enum", e);
                            }
                            break;
                        case "status":
                            try {
                                OrderStatus status = OrderStatus.valueOf(params.get("status").toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("status"), status));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderStatus Enum", e);
                            }
                            break;
                        case "user":
                            predicates.add(builder.equal(root.get("user").get("id"), Long.parseLong(params.get("user"))));
                            break;
                        case "invoice":
                            predicates.add(builder.equal(root.get("invoice").get("id"), Long.parseLong(params.get("invoice"))));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Order> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }

    @Override
    public List<Order> findAllBySupplierId(Long supplierId, Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(Order.class);

        Root<Order> root = criteria.from(Order.class);
        Join<Order, OrderDetails> orderDetailsJoin = root.join("orderDetailsSet");
        Join<OrderDetails, Product> productJoin = orderDetailsJoin.join("product");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));
        predicates.add(builder.equal(productJoin.get("supplier").get("id"), supplierId));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("type", "status", "user", "invoice").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "type":
                            try {
                                OrderType type = OrderType.valueOf(params.get("type").toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("type"), type));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderType Enum", e);
                            }
                            break;
                        case "status":
                            try {
                                OrderStatus status = OrderStatus.valueOf(params.get("status").toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("status"), status));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderStatus Enum", e);
                            }
                            break;
                        case "user":
                            predicates.add(builder.equal(root.get("user").get("id"), Long.parseLong(params.get("user"))));
                            break;
                        case "invoice":
                            predicates.add(builder.equal(root.get("invoice").get("id"), Long.parseLong(params.get("invoice"))));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).distinct(true);
        Query<Order> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
