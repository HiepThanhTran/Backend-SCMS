package com.fh.scm.services;

import com.fh.scm.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService extends UserDetailsService {

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
