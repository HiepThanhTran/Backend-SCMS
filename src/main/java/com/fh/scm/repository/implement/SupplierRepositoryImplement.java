package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Supplier;
import com.fh.scm.repository.SupplierRepository;
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
import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class SupplierRepositoryImplement implements SupplierRepository {

    private final LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Supplier get(UUID id) {
        Session session = this.getCurrentSession();

        return session.get(Supplier.class, id);
    }

    @Override
    public void insert(Supplier supplier) {
        Session session = this.getCurrentSession();
        session.save(supplier);
    }

    @Override
    public void update(Supplier supplier) {
        Session session = this.getCurrentSession();
        session.update(supplier);
    }

    @Override
    public void delete(UUID id) {
        Session session = this.getCurrentSession();
        Supplier supplier = session.get(Supplier.class, id);
        session.delete(supplier);
    }

    @Override
    public void softDelete(UUID id) {
        Session session = this.getCurrentSession();
        Supplier supplier = session.get(Supplier.class, id);
        supplier.setActive(false);
        session.update(supplier);
    }

    @Override
    public void insertOrUpdate(Supplier supplier) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(supplier);
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
    public Boolean exists(UUID id) {
        Session session = this.getCurrentSession();
        Supplier supplier = session.get(Supplier.class, id);

        return supplier != null;
    }

    @Override
    public List<Supplier> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);
        Root<Supplier> root = criteria.from(Supplier.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null) {
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
