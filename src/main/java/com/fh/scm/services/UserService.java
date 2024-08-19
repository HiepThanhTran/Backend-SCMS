package com.fh.scm.services;

import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    void createAdmin();

    boolean authenticateUser(String username, String password);

    void updateLastLogin(String username);

    UserResponse register(UserRequestRegister userRequestRegister);

    Boolean confirm(String username);

    UserResponse profile(String username);

    UserResponse updateProfile(String username, UserRequestUpdate userRequestUpdate);

    UserResponse getUserResponse(Long id);

    User get(Long id);

    User getByUsername(String username);

    User getByEmail(String email);

    void insert(User user);

    void updateProfile(User user);

    void insertOrUpdate(User user);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    Boolean exists(Long id);

    List<UserResponse> getAll(Map<String, String> params);
}
