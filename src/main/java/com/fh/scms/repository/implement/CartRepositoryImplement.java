package com.fh.scms.repository.implement;

import com.fh.scms.pojo.Cart;
import com.fh.scms.repository.CartRepository;
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
    public void save(Cart cart) {
        Session session = this.getCurrentSession();
        session.persist(cart);
    }

    @Override
    public void update(Cart cart) {
        Session session = this.getCurrentSession();
        session.merge(cart);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        Cart cart = session.get(Cart.class, id);
        session.delete(cart);
    }
}
