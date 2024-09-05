package com.fh.scms.repository.implement;

import com.fh.scms.enums.CriteriaType;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.repository.RatingRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;

@Repository
@Transactional
public class RatingRepositoryImplement implements RatingRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Rating findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Rating.class, id);
    }

    @Override
    public Rating findByUserIdAndSupplierId(Long userId, Long supplierId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Rating> criteria = builder.createQuery(Rating.class);
        Root<Rating> root = criteria.from(Rating.class);

        criteria.select(root).where(
                builder.equal(root.get("user").get("id"), userId),
                builder.equal(root.get("supplier").get("id"), supplierId)
        );
        Query<Rating> query = session.createQuery(criteria);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            LoggerFactory.getLogger(RatingRepositoryImplement.class).error("An error occurred while getting Rating by User and Supplier", e);
            return null;
        }
    }

    @Override
    public void save(Rating rating) {
        Session session = this.getCurrentSession();
        session.persist(rating);
    }

    @Override
    public void update(Rating rating) {
        Session session = this.getCurrentSession();
        session.merge(rating);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Rating rating = session.get(Rating.class, id);
        session.delete(rating);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Rating> root = criteria.from(Rating.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Rating> findAllWithFilter(Map<String, String> params) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Rating> criteria = builder.createQuery(Rating.class);
        Root<Rating> root = criteria.from(Rating.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("fromRating", "toRating", "criteria", "supplier", "user", "year").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "fromRating":
                            Predicate p1 = builder.greaterThanOrEqualTo(root.get("rating"), new BigDecimal(params.get("fromRating")));
                            predicates.add(p1);
                            break;
                        case "toRating":
                            Predicate p2 = builder.lessThanOrEqualTo(root.get("rating"), new BigDecimal(params.get("toRating")));
                            predicates.add(p2);
                            break;
                        case "year":
                            Predicate p5 = builder.equal(builder.function("YEAR", Integer.class, root.get("createdAt")), Integer.parseInt(params.get("year")));
                            predicates.add(p5);
                            break;
                        case "criteria":
                            try {
                                CriteriaType criteriaType = CriteriaType.valueOf(params.get("criteria").toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("criteria"), criteriaType));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(RatingRepositoryImplement.class).error("An error parse CriteriaType Enum", e);
                            }
                            break;
                        case "supplier":
                            Predicate p3 = builder.equal(root.get("supplier").get("id"), Long.parseLong(params.get("supplier")));
                            predicates.add(p3);
                            break;
                        case "user":
                            Predicate p4 = builder.equal(root.get("user").get("id"), Long.parseLong(params.get("user")));
                            predicates.add(p4);
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new)).orderBy(builder.desc(root.get("id")));
        Query<Rating> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }

    @Override
    public List<Supplier> getRankedSuppliers(CriteriaType criteriaType, @NotNull String sortOrder) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Supplier> criteria = builder.createQuery(Supplier.class);

        Root<Rating> ratingRoot = criteria.from(Rating.class);
        Join<Rating, Supplier> supplierJoin = ratingRoot.join("supplier");

        Predicate criteriaPredicate = builder.conjunction();

        if (criteriaType != null) {
            criteriaPredicate = builder.equal(ratingRoot.get("criteria"), criteriaType);
        }

        criteria.select(supplierJoin)
                .where(criteriaPredicate)
                .groupBy(supplierJoin)
                .orderBy(sortOrder.equalsIgnoreCase("desc")
                        ? builder.desc(builder.avg(ratingRoot.get("rating")))
                        : builder.asc(builder.avg(ratingRoot.get("rating"))));

        Query<Supplier> query = session.createQuery(criteria);

        return query.getResultList();
    }
}
