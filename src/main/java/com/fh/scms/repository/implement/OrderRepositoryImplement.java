package com.fh.scms.repository.implement;

import com.fh.scms.enums.OrderStatus;
import com.fh.scms.enums.OrderType;
import com.fh.scms.pojo.Order;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
            Arrays.asList("type", "status", "userId", "invoiceId").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "type":
                            try {
                                OrderType type = OrderType.valueOf(params.get(key).toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("type"), type));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderType Enum", e);
                            }
                            break;
                        case "status":
                            try {
                                OrderStatus status = OrderStatus.valueOf(params.get(key).toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("status"), status));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(OrderRepositoryImplement.class).error("An error parse OrderStatus Enum", e);
                            }
                            break;
                        case "userId":
                            predicates.add(builder.equal(root.get("user").get("id"), Long.parseLong(params.get(key))));
                            break;
                        case "invoiceId":
                            predicates.add(builder.equal(root.get("invoice").get("id"), Long.parseLong(params.get(key))));
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
}
