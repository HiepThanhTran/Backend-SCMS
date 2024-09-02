package com.fh.scms.repository.implement;

import com.fh.scms.pojo.OrderDetails;
import com.fh.scms.repository.OrderDetailsRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderDetailsRepositoryImplement implements OrderDetailsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public OrderDetails findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(OrderDetails.class, id);
    }

    @Override
    public void save(OrderDetails orderDetails) {
        Session session = this.getCurrentSession();
        session.persist(orderDetails);
    }

    @Override
    public void update(OrderDetails orderDetails) {
        Session session = this.getCurrentSession();
        session.merge(orderDetails);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        OrderDetails orderDetails = session.get(OrderDetails.class, id);
        session.delete(orderDetails);
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
    public List<OrderDetails> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<OrderDetails> criteria = builder.createQuery(OrderDetails.class);
        Root<OrderDetails> root = criteria.from(OrderDetails.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("fromPrice", "toPrice", "order", "product").forEach(key -> {
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
                        case "order":
                            predicates.add(builder.equal(root.get("order").get("id"), Long.parseLong(params.get("order"))));
                            break;
                        case "product":
                            predicates.add(builder.equal(root.get("product").get("id"), Long.parseLong(params.get("product"))));
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
