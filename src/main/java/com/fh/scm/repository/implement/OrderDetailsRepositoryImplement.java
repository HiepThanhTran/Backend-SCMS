package com.fh.scm.repository.implement;

import com.fh.scm.pojo.OrderDetails;
import com.fh.scm.repository.OrderDetailsRepository;
import com.fh.scm.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderDetailsRepositoryImplement implements OrderDetailsRepository {

    private final LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public OrderDetails get(UUID id) {
        Session session = this.getCurrentSession();

        return session.get(OrderDetails.class, id);
    }

    @Override
    public void insert(OrderDetails orderDetails) {
        Session session = this.getCurrentSession();
        session.save(orderDetails);
    }

    @Override
    public void update(OrderDetails orderDetails) {
        Session session = this.getCurrentSession();
        session.update(orderDetails);
    }

    @Override
    public void delete(UUID id) {
        Session session = this.getCurrentSession();
        OrderDetails orderDetails = session.get(OrderDetails.class, id);
        session.delete(orderDetails);
    }

    @Override
    public void softDelete(UUID id) {
        Session session = this.getCurrentSession();
        OrderDetails orderDetails = session.get(OrderDetails.class, id);
        orderDetails.setActive(false);
        session.update(orderDetails);
    }

    @Override
    public void insertOrUpdate(OrderDetails orderDetails) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(orderDetails);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<OrderDetails> root = criteria.from(OrderDetails.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public Boolean exists(UUID id) {
        Session session = this.getCurrentSession();
        OrderDetails orderDetails = session.get(OrderDetails.class, id);

        return orderDetails != null;
    }

    @Override
    public List<OrderDetails> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetails> criteria = builder.createQuery(OrderDetails.class);
        Root<OrderDetails> root = criteria.from(OrderDetails.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null) {
            Arrays.asList("fromPrice", "toPrice", "orderId", "productId").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "fromPrice":
                            String fromPrice = params.get("fromPrice");
                            predicates.add(builder.greaterThanOrEqualTo(root.get("unitPrice"), new BigDecimal(fromPrice)));
                            break;
                        case "toPrice":
                            String toPrice = params.get("toPrice");
                            predicates.add(builder.lessThanOrEqualTo(root.get("unitPrice"), new BigDecimal(toPrice)));
                            break;
                        case "orderId":
                            predicates.add(builder.equal(root.get("order").get("id"), UUID.fromString(params.get(key))));
                            break;
                        case "productId":
                            predicates.add(builder.equal(root.get("product").get("id"), UUID.fromString(params.get(key))));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<OrderDetails> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
