package com.fh.scms.services;

import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.dto.user.UserRequestUpdate;
import com.fh.scms.dto.user.UserResponse;
import com.fh.scms.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    User findByUsername(String username);

    void save(User user);

    void update(User user);

    void delete(Long id);

    Long count();

    List<User> findAllWithFilter(Map<String, String> params);

    UserResponse getUserResponse(User user);

    boolean authenticateUser(String username, String password);

    void updateLastLogin(String username);

    UserResponse registerUser(UserRequestRegister userRequestRegister);

    Boolean confirmUser(String username);

    UserResponse getProfileUser(String username);

    UserResponse updateProfileUser(String username, UserRequestUpdate userRequestUpdate);
}
