package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Shipper;
import com.fh.scm.repository.ShipperRepository;
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
public class ShipperRepositoryImplement implements ShipperRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Shipper get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Shipper.class, id);
    }

    @Override
    public void insert(Shipper shipper) {
        Session session = this.getCurrentSession();
        session.persist(shipper);
    }

    @Override
    public void update(Shipper shipper) {
        Session session = this.getCurrentSession();
        session.merge(shipper);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Shipper shipper = session.get(Shipper.class, id);
        session.delete(shipper);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Shipper shipper = session.get(Shipper.class, id);
        shipper.setActive(false);
        session.merge(shipper);
    }

    @Override
    public void insertOrUpdate(Shipper shipper) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(shipper);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Shipper> root = criteria.from(Shipper.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Shipper> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Shipper> criteria = builder.createQuery(Shipper.class);
        Root<Shipper> root = criteria.from(Shipper.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Shipper> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
