package com.fh.scms.repository.implement;

import com.fh.scms.enums.UserRole;
import com.fh.scms.pojo.User;
import com.fh.scms.repository.UserRepository;
import com.fh.scms.util.Pagination;
import com.fh.scms.util.Utils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
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
public class UserRepositoryImplement implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public User findById(Long id) {
        Session session = this.getCurrentSession();

        return session.get(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        try {
            criteria.select(root).where(builder.equal(root.get("username"), username));
            Query<User> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error occurred while getting user by username", e);
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        try {
            criteria.select(root).where(builder.equal(root.get("email"), email));
            Query<User> query = session.createQuery(criteria);

            return query.getSingleResult();
        } catch (NoResultException e) {
            LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error occurred while getting user by email", e);
            return null;
        }
    }

    @Override
    public void save(User user) {
        Session session = this.getCurrentSession();
        session.persist(user);
    }

    @Override
    public void update(User user) {
        Session session = this.getCurrentSession();
        session.merge(user);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);
    }

    @Override
    public Long count() {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(builder.count(root));
        Query<Long> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public List<User> findAllWithFilter(Map<String, String> params) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("active"), true));

        if (params != null && !params.isEmpty()) {
            Boolean isConfirm = Utils.parseBoolean(params.get("isConfirm"));
            if (isConfirm != null) {
                predicates.add(builder.equal(root.get("isConfirm"), isConfirm));
            }

            String roleStr = params.get("role");
            if (roleStr != null && !roleStr.isEmpty()) {
                try {
                    UserRole userRole = UserRole.valueOf(roleStr.toUpperCase(Locale.getDefault()));
                    predicates.add(builder.equal(root.get("role"), userRole));
                } catch (IllegalArgumentException e) {
                    LoggerFactory.getLogger(UserRepositoryImplement.class).error("An error parse Role Enum", e);
                }
            }
        }

        criteria.select(root).where(predicates.toArray(Predicate[]::new));
        Query<User> query = session.createQuery(criteria);
        Pagination.paginator(query, params);

        return query.getResultList();
    }
}
