package com.fh.scm.services;

import com.fh.scm.dto.user.UserRequestRegister;
import com.fh.scm.dto.user.UserRequestUpdate;
import com.fh.scm.dto.user.UserResponse;
import com.fh.scm.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    void createAdmin();

    boolean auth(String username, String password);

    void updateLastLogin(String username);

    UserResponse register(UserRequestRegister userRequestRegister);

    void confirm(String username);

    UserResponse profile(String username);

    UserResponse update(String username, UserRequestUpdate userRequestUpdate);

    UserResponse get(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    void insert(User user);

    void update(User user);

    void insertOrUpdate(User user);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    Boolean exists(Long id);

    List<UserResponse> getAll(Map<String, String> params);
}
