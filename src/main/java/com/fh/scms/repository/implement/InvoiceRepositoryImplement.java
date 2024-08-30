package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Invoice;
import com.fh.scms.repository.InvoiceRepository;
import com.fh.scms.util.Pagination;
import com.fh.scms.util.Utils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional
public class InvoiceRepositoryImplement implements InvoiceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Invoice findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Invoice.class, id);
    }

    @Override
    public Invoice findByOrderId(Long orderId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
        Root<Invoice> root = criteria.from(Invoice.class);

        try {
            criteria.select(root).where(builder.equal(root.get("order").get("id"), orderId));
            Query<Invoice> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(InvoiceRepositoryImplement.class).error("An error occurred while getting invoice by order", e);
            return null;
        }
    }

    @Override
    public void save(Invoice invoice) {
        Session session = this.getCurrentSession();
        session.persist(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        Session session = this.getCurrentSession();
        session.merge(invoice);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Invoice invoice = session.get(Invoice.class, id);
        session.delete(invoice);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Invoice> root = criteria.from(Invoice.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Invoice> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
        Root<Invoice> root = criteria.from(Invoice.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("userId", "isPaid", "taxId", "paymentTermsId", "fromCreatedAt", "toCreatedAt").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "userId":
                            predicates.add(builder.equal(root.get("user").get("id"), Long.parseLong(params.get(key))));
                            break;
                        case "isPaid":
                            Boolean isPaid = Utils.parseBoolean(params.get(key));
                            if (isPaid != null) {
                                predicates.add(builder.equal(root.get("isPaid"), isPaid));
                            }
                            break;
                        case "taxId":
                            predicates.add(builder.equal(root.get("tax").get("id"), Long.parseLong(params.get(key))));
                            break;
                        case "paymentTermsId":
                            predicates.add(builder.equal(root.get("paymentTerms").get("id"), Long.parseLong(params.get(key))));
                            break;
                        case "fromCreatedAt":
                            Date fromCreatedAt = Utils.parseDate(params.get(key));
                            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), fromCreatedAt));
                            break;
                        case "toCreatedAt":
                            Date toCreatedAt = Utils.parseDate(params.get(key));
                            predicates.add(builder.lessThanOrEqualTo(root.get("createdAt"), toCreatedAt));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Invoice> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
