package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Tag;
import com.fh.scms.repository.TagRepository;
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
public class TagRepositoryImplement implements TagRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Tag get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Tag.class, id);
    }

    @Override
    public List<Tag> getByProduct(Long productId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);

        Root<Product> root = criteria.from(Product.class);

        Join<Product, Tag> unitJoin = root.join("tagSet");

        criteria.select(unitJoin).where(builder.equal(root.get("id"), productId));
        Query<Tag> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public void insert(Tag tag) {
        Session session = this.getCurrentSession();
        session.persist(tag);
    }

    @Override
    public void update(Tag tag) {
        Session session = this.getCurrentSession();
        session.merge(tag);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        session.delete(tag);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        tag.setActive(false);
        session.merge(tag);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Tag> root = criteria.from(Tag.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Tag> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
        Root<Tag> root = criteria.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Tag> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
