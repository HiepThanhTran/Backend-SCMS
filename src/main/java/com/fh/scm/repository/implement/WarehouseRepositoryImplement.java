package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Warehouse;
import com.fh.scm.repository.WarehouseRepository;
import com.fh.scm.util.Pagination;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Transactional
public class WarehouseRepositoryImplement implements WarehouseRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Warehouse get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Warehouse.class, id);
    }

    @Override
    public void insert(Warehouse warehouse) {
        Session session = this.getCurrentSession();
        session.persist(warehouse);
    }

    @Override
    public void update(Warehouse warehouse) {
        Session session = this.getCurrentSession();
        session.merge(warehouse);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Warehouse warehouse = session.get(Warehouse.class, id);
        session.delete(warehouse);
        session.merge(warehouse);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Warehouse warehouse = session.get(Warehouse.class, id);
        warehouse.setActive(false);
        session.saveOrUpdate(warehouse);
    }

    @Override
    public void insertOrUpdate(Warehouse warehouse) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(warehouse);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Warehouse> root = criteria.from(Warehouse.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Warehouse> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Warehouse> criteria = builder.createQuery(Warehouse.class);
        Root<Warehouse> root = criteria.from(Warehouse.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }

            String location = params.get("location");
            if (location != null && !location.isEmpty()) {
                predicates.add(builder.like(root.get("location"), String.format("%%%s%%", location)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Warehouse> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
