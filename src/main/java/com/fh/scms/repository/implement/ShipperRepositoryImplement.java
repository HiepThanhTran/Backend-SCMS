package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Shipper;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.ShipperRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
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
    public Shipper findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Shipper.class, id);
    }

    @Override
    public Shipper findByUser(@NotNull User user) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Shipper> criteria = builder.createQuery(Shipper.class);
        Root<Shipper> root = criteria.from(Shipper.class);

        try {
            criteria.select(root).where(builder.equal(root.get("user").get("id"), user.getId()));
            Query<Shipper> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(Shipper shipper) {
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
    public List<Shipper> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Shipper> criteria = builder.createQuery(Shipper.class);
        Root<Shipper> root = criteria.from(Shipper.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

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
