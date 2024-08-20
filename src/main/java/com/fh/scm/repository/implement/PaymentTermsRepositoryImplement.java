package com.fh.scm.repository.implement;

import com.fh.scm.enums.PaymentTermType;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.repository.PaymentTermsRepository;
import com.fh.scm.util.Pagination;
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
public class PaymentTermsRepositoryImplement implements PaymentTermsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public PaymentTerms get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(PaymentTerms.class, id);
    }

    @Override
    public void insert(PaymentTerms paymentTerms) {
        Session session = this.getCurrentSession();
        session.persist(paymentTerms);
    }

    @Override
    public void update(PaymentTerms paymentTerms) {
        Session session = this.getCurrentSession();
        session.merge(paymentTerms);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        PaymentTerms paymentTerms = session.get(PaymentTerms.class, id);
        session.delete(paymentTerms);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        PaymentTerms paymentTerms = session.get(PaymentTerms.class, id);
        paymentTerms.setActive(false);
        session.merge(paymentTerms);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<PaymentTerms> root = criteria.from(PaymentTerms.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<PaymentTerms> getAll(Map<String, String> params) {
        Session session = Objects.requireNonNull(factory.getObject()).getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PaymentTerms> criteria = builder.createQuery(PaymentTerms.class);
        Root<PaymentTerms> root = criteria.from(PaymentTerms.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && !params.isEmpty()) {
            String typeStr = params.get("type");
            if (typeStr != null && !typeStr.isEmpty()) {
                try {
                    PaymentTermType type = PaymentTermType.valueOf(typeStr.toUpperCase(Locale.getDefault()));
                    predicates.add(builder.equal(root.get("type"), type));
                } catch (IllegalArgumentException e) {
                    LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error parse Role Enum", e);
                }
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<PaymentTerms> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
