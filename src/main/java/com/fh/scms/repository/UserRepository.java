package com.fh.scms.repository;

import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    User get(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    void insert(User user);

    void update(User user);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<User> getAll(Map<String, String> params);
}
