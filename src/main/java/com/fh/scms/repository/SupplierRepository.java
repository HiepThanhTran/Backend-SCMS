package com.fh.scms.repository;

import com.fh.scms.pojo.Supplier;
import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface SupplierRepository {

    Supplier get(Long id);

    Supplier getByUser(User user);

    Supplier getByPhone(String phone);

    void insert(Supplier supplier);

    void update(Supplier supplier);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<Supplier> getAll(Map<String, String> params);
}
