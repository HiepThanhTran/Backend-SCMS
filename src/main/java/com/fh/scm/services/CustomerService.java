package com.fh.scm.services;

import com.fh.scm.pojo.Customer;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CustomerService {

    Customer get(UUID id);

    void insert(Customer customer);

    void update(Customer customer);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(Customer customer);

    Long count();

    Boolean exists(UUID id);

    List<Customer> getAll(Map<String, String> params);
}
