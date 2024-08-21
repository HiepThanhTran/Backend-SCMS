package com.fh.scm.repository;

import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;

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
