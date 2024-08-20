package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Product;
import com.fh.scm.repository.ProductRepository;
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
import java.math.BigDecimal;
import java.util.*;

@Repository
@Transactional
public class ProductRepositoryImplement implements ProductRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Product get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Product.class, id);
    }

    @Override
    public void insert(Product Product) {
        Session session = this.getCurrentSession();
        session.persist(Product);
    }

    @Override
    public void update(Product Product) {
        Session session = this.getCurrentSession();
        session.merge(Product);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Product Product = session.get(Product.class, id);
        session.delete(Product);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Product Product = session.get(Product.class, id);
        Product.setActive(false);
        session.merge(Product);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Product> root = criteria.from(Product.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Product> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> root = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("name", "fromPrice", "toPrice", "categoryId").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "name":
                            Predicate p1 = builder.like(root.get("name"), String.format("%%%s%%", params.get(key)));
                            predicates.add(p1);
                            break;
                        case "fromPrice":
                            Predicate p2 = builder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(params.get(key)));
                            predicates.add(p2);
                            break;
                        case "toPrice":
                            Predicate p3 = builder.lessThanOrEqualTo(root.get("price"), new BigDecimal(params.get(key)));
                            predicates.add(p3);
                            break;
                        case "categoryId":
                            Predicate p4 = builder.equal(root.get("category").get("id"), Long.parseLong(params.get(key)));
                            predicates.add(p4);
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Product> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
