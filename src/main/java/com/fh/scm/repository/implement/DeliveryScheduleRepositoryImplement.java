package com.fh.scm.repository.implement;

import com.fh.scm.enums.DeliveryMethodType;
import com.fh.scm.pojo.DeliverySchedule;
import com.fh.scm.repository.DeliveryScheduleRepository;
import com.fh.scm.util.Pagination;
import com.fh.scm.util.Utils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Transactional
public class DeliveryScheduleRepositoryImplement implements DeliveryScheduleRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public DeliverySchedule get(Long id) {
        Session session = this.getCurrentSession();

        return session.get(DeliverySchedule.class, id);
    }

    @Override
    public void insert(DeliverySchedule deliverySchedule) {
        Session session = this.getCurrentSession();
        session.save(deliverySchedule);
    }

    @Override
    public void update(DeliverySchedule deliverySchedule) {
        Session session = this.getCurrentSession();
        session.update(deliverySchedule);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        DeliverySchedule deliverySchedule = session.get(DeliverySchedule.class, id);
        session.delete(deliverySchedule);
    }

    @Override
    public void softDelete(Long id) {
        Session session = this.getCurrentSession();
        DeliverySchedule deliverySchedule = session.get(DeliverySchedule.class, id);
        deliverySchedule.setActive(false);
        session.update(deliverySchedule);
    }

    @Override
    public void insertOrUpdate(DeliverySchedule deliverySchedule) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(deliverySchedule);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<DeliverySchedule> root = criteria.from(DeliverySchedule.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public Boolean exists(Long id) {
        Session session = this.getCurrentSession();
        DeliverySchedule deliverySchedule = session.get(DeliverySchedule.class, id);

        return deliverySchedule != null;
    }

    @Override
    public List<DeliverySchedule> getAll(Map<String, String> params) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<DeliverySchedule> criteria = builder.createQuery(DeliverySchedule.class);
        Root<DeliverySchedule> root = criteria.from(DeliverySchedule.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Arrays.asList("fromDate", "toDate", "method").forEach(key -> {
                if (params.containsKey(key) && !params.get(key).isEmpty()) {
                    switch (key) {
                        case "fromDate":
                            try {
                                LocalDateTime fromDate = Utils.parseDate(params.get(key));
                                predicates.add(builder.greaterThanOrEqualTo(root.get("scheduledDate"), fromDate));
                            } catch (Exception e) {
                                LoggerFactory.getLogger(DeliveryScheduleRepositoryImplement.class).error("An error parse LocalDateTime", e);
                            }
                            break;
                        case "toDate":
                            try {
                                LocalDateTime toDate = Utils.parseDate(params.get(key));
                                predicates.add(builder.lessThanOrEqualTo(root.get("scheduledDate"), toDate));
                            } catch (Exception e) {
                                LoggerFactory.getLogger(DeliveryScheduleRepositoryImplement.class).error("An error parse LocalDateTime", e);
                            }
                            break;
                        case "method":
                            try {
                                DeliveryMethodType method = DeliveryMethodType.valueOf(params.get(key).toUpperCase(Locale.getDefault()));
                                predicates.add(builder.equal(root.get("method"), method));
                            } catch (IllegalArgumentException e) {
                                LoggerFactory.getLogger(DeliveryScheduleRepositoryImplement.class).error("An error parse DeliveryMethodType Enum", e);
                            }
                            break;
                    }
                }
            });
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<DeliverySchedule> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
