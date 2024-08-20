package com.fh.scm.repository.implement;

import com.fh.scm.pojo._System;
import com.fh.scm.repository._SystemRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Objects;

@Repository
@Transactional
public class _SystemRepositoryImplement implements _SystemRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public Boolean isExist(String name) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<_System> root = criteria.from(_System.class);

        criteria.where(builder.equal(root.get("name"), name)).select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult() > 0;
    }

    @Override
    public void insert(String name) {
        Session session = this.getCurrentSession();
        _System system = _System.builder().name(name).build();
        session.persist(system);
    }
}
