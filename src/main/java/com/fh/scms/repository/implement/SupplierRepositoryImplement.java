package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.SupplierRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
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
public class SupplierRepositoryImplement implements SupplierRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Supplier findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Supplier.class, id);
    }

    @Override
    public Supplier findByUser(@NotNull User user) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        try {
            criteria.select(root).where(builder.equal(root.get("user").get("id"), user.getId()));
            Query<Supplier> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Supplier supplier) {
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
    public List<Supplier> findAllWithFilter(Map<String, String> params) {
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
