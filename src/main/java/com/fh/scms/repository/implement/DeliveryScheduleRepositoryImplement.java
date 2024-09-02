package com.fh.scms.repository.implement;

import com.fh.scms.enums.DeliveryMethodType;
import com.fh.scms.pojo.DeliverySchedule;
import com.fh.scms.repository.DeliveryScheduleRepository;
import com.fh.scms.util.Pagination;
import com.fh.scms.util.Utils;
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
import java.time.LocalDate;
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
    public DeliverySchedule findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(DeliverySchedule.class, id);
    }

    @Override
    public void save(DeliverySchedule deliverySchedule) {
        Session session = this.getCurrentSession();
        session.persist(deliverySchedule);
    }

    @Override
    public void update(DeliverySchedule deliverySchedule) {
        Session session = this.getCurrentSession();
        session.merge(deliverySchedule);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        DeliverySchedule deliverySchedule = session.get(DeliverySchedule.class, id);
        session.delete(deliverySchedule);
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
    public List<DeliverySchedule> findAllWithFilter(Map<String, String> params) {
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
                            LocalDate fromDate = LocalDate.parse(params.get("fromDate"));
                            predicates.add(builder.greaterThanOrEqualTo(root.get("scheduledDate"), fromDate));
                            break;
                        case "toDate":
                            LocalDate toDate = LocalDate.parse(params.get("toDate"));
                            predicates.add(builder.lessThanOrEqualTo(root.get("scheduledDate"), toDate));
                            break;
                        case "method":
                            try {
                                DeliveryMethodType method = DeliveryMethodType.valueOf(params.get("method").toUpperCase(Locale.getDefault()));
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
