package com.fh.scms.repository;

import com.fh.scms.pojo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {

    Customer get(Long id);

    Customer getByPhone(String phone);

    void insert(Customer customer);

    void update(Customer customer);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Customer> getAll(Map<String, String> params);
}
