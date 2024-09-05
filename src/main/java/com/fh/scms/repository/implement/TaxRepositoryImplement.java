package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Tax;
import com.fh.scms.repository.TaxRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Transactional
public class TaxRepositoryImplement implements TaxRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Tax findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Tax.class, id);
    }

    @Override
    public Tax findByRegion(String region) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tax> criteria = builder.createQuery(Tax.class);
        Root<Tax> root = criteria.from(Tax.class);

        try {
            criteria.select(root).where(builder.equal(root.get("region"), region));
            Query<Tax> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(TaxRepositoryImplement.class).error("An error occurred while getting tax by region", e);
            return null;
        }
    }

    @Override
    public void save(Tax tax) {
        Session session = this.getCurrentSession();
        session.persist(tax);
    }

    @Override
    public void update(Tax tax) {
        Session session = this.getCurrentSession();
        session.merge(tax);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Tax tax = session.get(Tax.class, id);
        session.delete(tax);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Tax> root = criteria.from(Tax.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Tax> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tax> criteria = builder.createQuery(Tax.class);
        Root<Tax> root = criteria.from(Tax.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String region = params.get("region");
            if (region != null && !region.isEmpty()) {
                predicates.add(builder.equal(root.get("region"), region));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).orderBy(builder.desc(root.get("id")));
        Query<Tax> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
