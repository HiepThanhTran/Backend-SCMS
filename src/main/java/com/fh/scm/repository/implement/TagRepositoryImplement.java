package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Tag;
import com.fh.scm.repository.TagRepository;
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
public class TagRepositoryImplement implements TagRepository {

    private final LocalSessionFactoryBean factory;
    
    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Tag get(UUID id) {
        Session session = this.getCurrentSession();

        return session.get(Tag.class, id);
    }

    @Override
    public void insert(Tag tag) {
        Session session = this.getCurrentSession();
        session.save(tag);
    }

    @Override
    public void update(Tag tag) {
        Session session = this.getCurrentSession();
        session.update(tag);
    }

    @Override
    public void delete(UUID id) {
        Session session = this.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        session.delete(tag);
    }

    @Override
    public void softDelete(UUID id) {
        Session session = this.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        tag.setActive(false);
        session.update(tag);
    }

    @Override
    public void insertOrUpdate(Tag tag) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(tag);
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
    public Boolean exists(UUID id) {
        Session session = this.getCurrentSession();
        Tag tag = session.get(Tag.class, id);

        return tag != null;
    }

    @Override
    public List<Tag> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
        Root<Tag> root = criteria.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null) {
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
