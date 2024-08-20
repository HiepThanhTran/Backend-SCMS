package com.fh.scm.services;

import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    UserResponse getUserResponse(User user);

    boolean authenticateUser(String username, String password);

    void updateLastLogin(String username);

    UserResponse register(UserRequestRegister userRequestRegister);

    Boolean confirmUser(String username);

    UserResponse getProfileUser(String username);

    UserResponse updateProfileUser(String username, UserRequestUpdate userRequestUpdate);

    User get(Long id);

    User getByUsername(String username);

    void insert(User user);

    void update(User user);

    void delete(Long id);

    void softDelete(Long id);

    Long count();

    List<User> getAll(Map<String, String> params);
}
