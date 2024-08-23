package com.fh.scms.services;

import com.fh.scms.pojo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    Customer findById(Long id);

    Customer findByPhone(String phone);

    void save(Customer customer);

    void update(Customer customer);

    void delete(Long id);

    Long count();

    List<Customer> findAllWithFilter(Map<String, String> params);
}
