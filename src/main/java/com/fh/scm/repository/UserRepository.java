package com.fh.scm.repository;

import com.fh.scm.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    User get(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    void insert(User user);

    void update(User user);

    void insertOrUpdate(User user);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<User> getAll(Map<String, String> params);
}
