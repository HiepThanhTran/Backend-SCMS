package com.fh.scm.repository.implement;

import com.fh.scm.enums.Role;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.util.Pagination;
import com.fh.scm.util.Utils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryImplement implements UserRepository {

    private final LocalSessionFactoryBean factory;
    private final BCryptPasswordEncoder passEncoder;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getByUsername(username);

        if (u == null) {
            return false;
        }

        return this.passEncoder.matches(password, u.getPassword());
    }

    @Override
    public User get(UUID id) {
        Session session = this.getCurrentSession();

        return session.get(User.class, id);
    }

    @Override
    public User getByUsername(String username) {
        Session session = this.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.where(builder.equal(root.get("username"), username));
        Query<User> query = session.createQuery(criteria);

        return query.getSingleResult();
    }

    @Override
    public void insert(User user) {
        Session session = this.getCurrentSession();
        session.save(user);
    }

    @Override
    public void update(User user) {
        Session session = this.getCurrentSession();
        session.update(user);
    }

    @Override
    public void delete(UUID id) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, id);
        session.delete(user);
    }

    @Override
    public void softDelete(UUID id) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, id);
        user.setActive(false);
        session.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        Session session = this.getCurrentSession();
        session.saveOrUpdate(user);
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
    public Boolean exists(UUID id) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, id);

        return user != null;
    }

    @Override
    public List<User> getAll(Map<String, String> params) {
        Session session = Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("isActive"), true));

        if (params != null) {
            Boolean isConfirm = Utils.parseBoolean(params.get("isConfirm"));
            if (isConfirm != null) {
                predicates.add(builder.equal(root.get("isConfirm"), isConfirm));
            }

            String roleStr = params.get("role");
            if (roleStr != null && !roleStr.isEmpty()) {
                try {
                    Role role = Role.valueOf(roleStr.toUpperCase(Locale.getDefault()));
                    predicates.add(builder.equal(root.get("role"), role));
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
