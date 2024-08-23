package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Inventory;
import com.fh.scms.repository.InventoryRepository;
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
public class InventoryRepositoryImplement implements InventoryRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Inventory get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(Inventory.class, id);
    }

    @Override
    public void insert(Inventory inventory) {
        Session session = this.getCurrentSession();
        session.persist(inventory);
    }

    @Override
    public void update(Inventory inventory) {
        Session session = this.getCurrentSession();
        session.merge(inventory);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Inventory inventory = session.get(Inventory.class, id);
        session.delete(inventory);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        Inventory inventory = session.get(Inventory.class, id);
        inventory.setActive(false);
        session.merge(inventory);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Inventory> root = criteria.from(Inventory.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<Inventory> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Inventory> criteria = builder.createQuery(Inventory.class);
        Root<Inventory> root = criteria.from(Inventory.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            String name = params.get("name");
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), String.format("%%%s%%", name)));
            }

            String warehouseId = params.get("warehouseId");
            if (warehouseId != null && !warehouseId.isEmpty()) {
                predicates.add(builder.equal(root.get("warehouse").get("id"), Long.parseLong(warehouseId)));
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<Inventory> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
