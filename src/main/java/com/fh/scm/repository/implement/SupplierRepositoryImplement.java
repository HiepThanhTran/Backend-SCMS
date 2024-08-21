package com.fh.scm.repository.implement;

import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.SupplierRepository;
import com.fh.scm.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
@Transactional
public class SupplierRepositoryImplement implements SupplierRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Supplier get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Supplier.class, id);
    }

    @Override
    public Supplier getByUser(User user) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        try {
            criteria.select(root).where(builder.equal(root.get("user").get("id"), user.getId()));
            Query<Supplier> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error occurred while getting supplier by user", e);
            return null;
        }
    }

    @Override
    public Supplier getByPhone(String phone) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        try {
            criteria.select(root).where(builder.equal(root.get("phone"), phone));
            Query<Supplier> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error occurred while getting supplier by phone", e);
            return null;
        }
    }

    @Override
    public void insert(Supplier supplier) {
        Session session = this.getCurrentSession();
        session.persist(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        Session session = this.getCurrentSession();
        session.merge(supplier);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Supplier supplier = session.get(Supplier.class, id);
        session.delete(supplier);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Supplier supplier = session.get(Supplier.class, id);
        supplier.setActive(false);
        session.merge(supplier);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Supplier> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Supplier> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
