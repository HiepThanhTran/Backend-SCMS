package com.fh.scm.repository.implement;

import com.fh.scm.pojo.Cart;
import com.fh.scm.repository.CartRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
@Transactional
public class CartRepositoryImplement implements CartRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(this.factory.getObject()).getCurrentSession();
    }

    @Override
    public void insert(Cart cart) {
        Session session = this.getCurrentSession();
        session.persist(cart);
    }
}
