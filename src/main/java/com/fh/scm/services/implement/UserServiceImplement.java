package com.fh.scm.services.implement;

import com.fh.scm.pojo.User;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid User!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
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
