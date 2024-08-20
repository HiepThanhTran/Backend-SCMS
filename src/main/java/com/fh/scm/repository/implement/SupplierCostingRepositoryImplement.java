package com.fh.scm.repository.implement;

import com.fh.scm.pojo.SupplierCosting;
import com.fh.scm.repository.SupplierCostingRepository;
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
public class SupplierCostingRepositoryImplement implements SupplierCostingRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public SupplierCosting get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(SupplierCosting.class, id);
    }

    @Override
    public void insert(SupplierCosting supplierCosting) {
        Session session = this.getCurrentSession();
        session.persist(supplierCosting);
    }

    @Override
    public void update(SupplierCosting supplierCosting) {
        Session session = this.getCurrentSession();
        session.merge(supplierCosting);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        SupplierCosting supplierCosting = session.get(SupplierCosting.class, id);
        session.delete(supplierCosting);
    }

    @Override
    public void insertOrUpdate(SupplierCosting supplierCosting) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(supplierCosting);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<SupplierCosting> root = criteria.from(SupplierCosting.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<SupplierCosting> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SupplierCosting> criteria = builder.createQuery(SupplierCosting.class);
        Root<SupplierCosting> root = criteria.from(SupplierCosting.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("fromPrice", "toPrice", "productId", "supplierId").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "fromPrice":
                            String fromPrice = params.get("fromPrice");
                            predicates.add(builder.greaterThanOrEqualTo(root.get("unitPrice"), new BigDecimal(fromPrice)));
                            break;
                        case "toPrice":
                            String toPrice = params.get("toPrice");
                            predicates.add(builder.lessThanOrEqualTo(root.get("unitPrice"), new BigDecimal(toPrice)));
                            break;
                        case "productId":
                            predicates.add(builder.equal(root.get("product").get("id"), Long.parseLong(params.get(key))));
                            break;
                        case "supplierId":
                            predicates.add(builder.equal(root.get("supplier").get("id"), Long.parseLong(params.get(key))));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<SupplierCosting> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
