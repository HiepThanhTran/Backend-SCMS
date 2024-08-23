package com.fh.scms.repository;

import com.fh.scms.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserRepository {

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    void save(User user);

    void update(User user);

    void delete(Long id);

    Long count();

    List<User> findAllWithFilter(Map<String, String> params);
}
