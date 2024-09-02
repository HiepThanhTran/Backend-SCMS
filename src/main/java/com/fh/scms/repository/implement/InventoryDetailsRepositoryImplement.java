package com.fh.scms.repository.implement;

import com.fh.scms.pojo.InventoryDetails;
import com.fh.scms.repository.InventoryDetailsRepository;
import com.fh.scms.util.Pagination;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Transactional
public class InventoryDetailsRepositoryImplement implements InventoryDetailsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public Float getTotalQuantityByWarehouseId(Long warehouseId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Float> criteria = builder.createQuery(Float.class);
        Root<InventoryDetails> root = criteria.from(InventoryDetails.class);

        criteria.select(builder.sum(root.get("quantity")));
        criteria.where(builder.equal(root.get("inventory").get("warehouse").get("id"), warehouseId));
        Query<Float> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public InventoryDetails findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(InventoryDetails.class, id);
    }

    @Override
    public InventoryDetails findByInventoryIdAndProductId(Long inventoryId, Long productId) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryDetails> criteria = builder.createQuery(InventoryDetails.class);
        Root<InventoryDetails> root = criteria.from(InventoryDetails.class);

        try {
            criteria.select(root).where(
                    builder.equal(root.get("inventory").get("id"), inventoryId),
                    builder.equal(root.get("product").get("id"), productId)
            );
            Query<InventoryDetails> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(InventoryDetails inventoryDetails) {
        Session session = this.getCurrentSession();
        session.persist(inventoryDetails);
    }

    @Override
    public void update(InventoryDetails inventoryDetails) {
        Session session = this.getCurrentSession();
        session.merge(inventoryDetails);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        InventoryDetails inventoryDetails = session.get(InventoryDetails.class, id);
        session.delete(inventoryDetails);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<InventoryDetails> root = criteria.from(InventoryDetails.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<InventoryDetails> findAllWithFilter(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<InventoryDetails> criteria = builder.createQuery(InventoryDetails.class);
        Root<InventoryDetails> root = criteria.from(InventoryDetails.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("inventory", "product").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "inventory":
                            predicates.add(builder.equal(root.get("inventory").get("id"), Long.parseLong(params.get("inventory"))));
                            break;
                        case "product":
                            predicates.add(builder.equal(root.get("product").get("id"), Long.parseLong(params.get("product"))));
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<InventoryDetails> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
