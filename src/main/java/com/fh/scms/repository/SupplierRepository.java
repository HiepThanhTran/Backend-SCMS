package com.fh.scms.repository;

import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface SupplierRepository {

    Supplier findById(Long id);

    Supplier findByUser(User user);

    Supplier findByPhone(String phone);

    void save(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    Long count();

    List<Supplier> findAllWithFilter(Map<String, String> params);
}
