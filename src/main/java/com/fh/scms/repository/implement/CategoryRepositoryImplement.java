package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Category;
import com.fh.scms.repository.CategoryRepository;
import com.fh.scms.util.Pagination;
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
public class CategoryRepositoryImplement implements CategoryRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Category get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Category.class, id);
    }

    @Override
    public void insert(Category category) {
        Session session = this.getCurrentSession();
        session.persist(category);
    }

    @Override
    public void update(Category category) {
        Session session = this.getCurrentSession();
        session.merge(category);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Category category = session.get(Category.class, id);
        session.delete(category);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Category category = session.get(Category.class, id);
        category.setActive(false);
        session.merge(category);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Category> root = criteria.from(Category.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Category> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
        Root<Category> root = criteria.from(Category.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Category> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
