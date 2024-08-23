package com.fh.scms.repository;

import com.fh.scms.pojo.CartDetails;

public interface CartDetailsRepository {

    void persist(CartDetails cartDetails);

    void merge(CartDetails cartDetails);

    void delete(Long id);
}
