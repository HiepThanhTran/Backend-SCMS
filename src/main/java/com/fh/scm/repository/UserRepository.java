package com.fh.scm.repository;

import com.fh.scm.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserRepository {

    boolean authUser(String username, String password);

    User get(UUID id);

    User getByUsername(String username);

    void insert(User user);

    void update(User user);

    void delete(UUID id);

    void softDelete(UUID id);

    void insertOrUpdate(User user);

    Long count();

    Boolean exists(UUID id);

    List<User> getAll(Map<String, String> params);
}
