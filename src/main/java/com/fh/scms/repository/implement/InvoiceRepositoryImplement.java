package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Invoice;
import com.fh.scms.repository.InvoiceRepository;
import com.fh.scms.util.Pagination;
import com.fh.scms.util.Utils;
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
    public Invoice get(Long id) {
        Session session = getCurrentSession();

        return session.get(Invoice.class, id);
    }

    @Override
    public void insert(Invoice invoice) {
        Session session = getCurrentSession();
        session.persist(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        Session session = getCurrentSession();
        session.merge(invoice);
    }

    @Override
    public void delete(Long id) {
        Session session = getCurrentSession();
        Invoice invoice = session.get(Invoice.class, id);
        session.delete(invoice);
    }

    @Override
    public void softDelete(Long id) {
        Session session = getCurrentSession();
        Invoice invoice = session.get(Invoice.class, id);
        invoice.setActive(false);
        session.merge(invoice);
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
    public List<Invoice> getAll(Map<String, String> params) {
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Invoice> criteria = builder.createQuery(Invoice.class);
        Root<Invoice> root = criteria.from(Invoice.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("isPaid", "taxId", "paymentTermsId", "fromCreatedAt", "toCreatedAt").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
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
                            LocalDateTime fromCreatedAt = Utils.parseLocalDateTime(params.get(key));
                            predicates.add(builder.greaterThanOrEqualTo(root.get("createdAt"), fromCreatedAt));
                            break;
                        case "toCreatedAt":
                            LocalDateTime toCreatedAt = Utils.parseLocalDateTime(params.get(key));
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
