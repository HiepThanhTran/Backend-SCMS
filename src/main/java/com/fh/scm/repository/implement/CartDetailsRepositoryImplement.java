package com.fh.scm.repository.implement;

import com.fh.scm.pojo.CartDetails;
import com.fh.scm.repository.CartDetailsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Repository
@Transactional
public class CartDetailsRepositoryImplement implements CartDetailsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private Session getCurrentSession() {
        return Objects.requireNonNull(factory.getObject()).getCurrentSession();
    }

    @Override
    public void persist(CartDetails cartDetails) {
        Session session = this.getCurrentSession();
        session.persist(cartDetails);
    }

    @Override
    public void merge(CartDetails cartDetails) {
        Session session = this.getCurrentSession();
        session.merge(cartDetails);
    }

    @Override
    public void delete(Long id) {
        Session session = this.getCurrentSession();
        CartDetails cartDetails = session.get(CartDetails.class, id);
        session.delete(cartDetails);
    }
}
