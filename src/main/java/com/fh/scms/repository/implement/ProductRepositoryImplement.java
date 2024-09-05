package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Product;
import com.fh.scms.pojo.Tag;
import com.fh.scms.repository.ProductRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ProductRepositoryImplement implements ProductRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Product findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Product.class, id);
    }

    @Override
    public void save(Product Product) {
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
    public List<Product> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        Root<Product> root = criteria.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("name", "fromPrice", "toPrice", "category", "unit", "tags", "supplier").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "name":
                            Predicate p1 = builder.like(root.get("name"), String.format("%%%s%%", params.get("name")));
                            predicates.add(p1);
                            break;
                        case "fromPrice":
                            Predicate p2 = builder.greaterThanOrEqualTo(root.get("price"), new BigDecimal(params.get("fromPrice")));
                            predicates.add(p2);
                            break;
                        case "toPrice":
                            Predicate p3 = builder.lessThanOrEqualTo(root.get("price"), new BigDecimal(params.get("toPrice")));
                            predicates.add(p3);
                            break;
                        case "category":
                            Predicate p4 = builder.equal(root.get("category").get("id"), Long.parseLong(params.get("category")));
                            predicates.add(p4);
                            break;
                        case "unit":
                            Predicate p5 = builder.equal(root.get("unit").get("id"), Long.parseLong(params.get("unit")));
                            predicates.add(p5);
                            break;
                        case "tags":
                            List<Long> tagIdList = Arrays.stream(params.get("tags").split(","))
                                    .map(Long::parseLong).collect(Collectors.toList());

                            Join<Product, Tag> tagJoin = root.join("tagSet");
                            Predicate tagPredicate = tagJoin.get("id").in(tagIdList);
                            predicates.add(tagPredicate);
                            break;
                        case "supplier":
                            Predicate p6 = builder.equal(root.get("supplier").get("id"), Long.parseLong(params.get("supplier")));
                            predicates.add(p6);
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).distinct(true).orderBy(builder.desc(root.get("id")));
        Query<Product> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
