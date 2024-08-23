package com.fh.scms.repository;

import com.fh.scms.pojo.CartDetails;

public interface CartDetailsRepository {

    void save(CartDetails cartDetails);

    void update(CartDetails cartDetails);

    void delete(Long id);
}
