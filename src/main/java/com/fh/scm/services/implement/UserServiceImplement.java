package com.fh.scm.services.implement;

import com.fh.scm.dto.user.UserRegisterRequest;
import com.fh.scm.dto.user.UserResponse;
import com.fh.scm.enums.Role;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.services.CloudinaryService;
import com.fh.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid User!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public void createAdmin() {
        User existingAdmin = userRepository.getByUsername("admin");
        if (existingAdmin != null) {
            System.out.println("Admin user already exists.");
            return;
        }

        User admin = User.builder()
                .email("admin@scm.com")
                .username("admin")
                .password(passwordEncoder.encode("admin@123"))
                .role(Role.ADMIN)
                .isConfirm(true)
                .build();

        this.userRepository.insert(admin);
        System.out.println("Admin user created successfully.");
    }

    @Override
    public boolean auth(String username, String password) {
        return this.userRepository.auth(username, password);
    }

    @Override
    public UserResponse register(UserRegisterRequest userRegisterRequest) {
        MultipartFile avatar = userRegisterRequest.getAvatar();
        String avatarUrl = null;
        if (avatar != null && !avatar.isEmpty()) {
            avatarUrl = this.cloudinaryService.uploadImage(avatar);
        }

        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        User user = User.builder()
                .email(userRegisterRequest.getEmail())
                .username(userRegisterRequest.getUsername())
                .password(hashedPassword)
                .role(userRegisterRequest.getRole())
                .avatar(avatarUrl)
                .build();

        if (user.getRole() == Role.ADMIN) {
            user.setIsConfirm(true);
        }

        this.userRepository.insert(user);

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public UserResponse profile(String username) {
        User user = this.userRepository.getByUsername(username);

        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public User get(UUID id) {
        return this.userRepository.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public void insert(User user) {
        this.userRepository.insert(user);
    }

    @Override
    public void update(User user) {
        this.userRepository.update(user);
    }

    @Override
    public void delete(UUID id) {
        this.userRepository.delete(id);
    }

    @Override
    public void softDelete(UUID id) {
        this.userRepository.softDelete(id);
    }

    @Override
    public void insertOrUpdate(User user) {
        this.userRepository.insertOrUpdate(user);
    }

    @Override
    public Long count() {
        return this.userRepository.count();
    }

    @Override
    public Boolean exists(UUID id) {
        return this.userRepository.exists(id);
    }

    @Override
    public List<User> getAll(Map<String, String> params) {
        return this.userRepository.getAll(params);
    }
}
