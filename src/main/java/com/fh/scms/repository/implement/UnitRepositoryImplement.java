package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Unit;
import com.fh.scms.repository.UnitRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Transactional
public class UnitRepositoryImplement implements UnitRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Unit get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Unit.class, id);
    }

    @Override
    public List<Unit> getByProduct(Long productId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Unit> criteria = builder.createQuery(Unit.class);

        Root<Product> root = criteria.from(Product.class);

        Join<Product, Unit> unitJoin = root.join("unitSet");

        criteria.select(unitJoin).where(builder.equal(root.get("id"), productId));
        Query<Unit> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void insert(Unit unit) {
        Session session = this.getCurrentSession();
        session.persist(unit);
    }

    @Override
    public void update(Unit unit) {
        Session session = this.getCurrentSession();
        session.merge(unit);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Unit unit = session.get(Unit.class, id);
        session.delete(unit);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Unit unit = session.get(Unit.class, id);
        unit.setActive(false);
        session.merge(unit);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Unit> root = criteria.from(Unit.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Unit> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Unit> criteria = builder.createQuery(Unit.class);
        Root<Unit> root = criteria.from(Unit.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Unit> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
