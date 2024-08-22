package com.fh.scm.repository;

import com.fh.scm.pojo.CartDetails;

public interface CartDetailsRepository {

    void persist(CartDetails cartDetails);

    void merge(CartDetails cartDetails);

    void delete(Long id);
}
