package com.fh.scm.services.implement;

import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.enums.UserRole;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.Customer;
import com.fh.scm.pojo.Shipper;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.CustomerRepository;
import com.fh.scm.repository.ShipperRepository;
import com.fh.scm.repository.SupplierRepository;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userDetailsService")
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private CustomerRepository customerRepository;
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
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public void createAdmin() {
        Map<String, String> params = Map.of("role", UserRole.ROLE_ADMIN.name());
        List<User> users = this.userRepository.getAll(params);
        if (!users.isEmpty()) {
            System.out.println("Admin user already exists.");
            return;
        }

        User admin = User.builder()
                .email("admin@scm.com")
                .username("adminscm")
                .password(passwordEncoder.encode("admin@123"))
                .userRole(UserRole.ROLE_ADMIN)
                .isConfirm(true)
                .build();

        this.userRepository.insertOrUpdate(admin);
        System.out.println("Admin user created successfully.");
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = this.userRepository.getByUsername(username);

        return user != null && this.passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void updateLastLogin(String username) {
        User user = this.userRepository.getByUsername(username);
        user.setLastLogin(LocalDateTime.now());
        this.userRepository.update(user);
    }

    @Override
    public UserResponse register(UserRequestRegister userRequestRegister) {
        User user;

        user = this.userRepository.getByUsername(userRequestRegister.getUsername());
        if (user != null) {
            throw new UserException("Tên đăng nhập đã tồn tại");
        }

        user = this.userRepository.getByUsername(userRequestRegister.getEmail());
        if (user != null) {
            throw new UserException("Email đã được liên kết đến tài khoản khác");
        }

        String avatarUrl = null;
        if (userRequestRegister.getAvatar() != null && !userRequestRegister.getAvatar().isEmpty()) {
            avatarUrl = this.cloudinaryService.uploadImage(userRequestRegister.getAvatar());
        }

        user = User.builder()
                .email(userRequestRegister.getEmail())
                .username(userRequestRegister.getUsername())
                .password(passwordEncoder.encode(userRequestRegister.getPassword()))
                .userRole(userRequestRegister.getUserRole())
                .avatar(avatarUrl)
                .build();

        switch (user.getUserRole()) {
            case ROLE_ADMIN:
                user.setIsConfirm(true);
                break;
            case ROLE_SUPPLIER:
                Supplier supplier;
                supplier = this.supplierRepository.getByPhone(userRequestRegister.getPhone());
                if (supplier != null) {
                    throw new UserException("Số điện thoại đã được liên kết đến tài khoản khác");
                }

                supplier = Supplier.builder()
                        .name(userRequestRegister.getName())
                        .address(userRequestRegister.getAddress())
                        .phone(userRequestRegister.getPhone())
                        .contactInfo(userRequestRegister.getContactInfo())
                        .supplierPaymentTermsSet(userRequestRegister.getSupplierPaymentTermsSet())
                        .user(user)
                        .build();
                user.setSupplier(supplier);
                break;
            case ROLE_SHIPPER:
                Shipper shipper = Shipper.builder()
                        .name(userRequestRegister.getName())
                        .contactInfo(userRequestRegister.getContactInfo())
                        .user(user)
                        .build();
                user.setShipper(shipper);
                break;
            case ROLE_CUSTOMER:
                Customer customer;
                customer = this.customerRepository.getByPhone(userRequestRegister.getPhone());
                if (customer != null) {
                    throw new UserException("Số điện thoại đã được liên kết đến tài khoản khác");
                }

                customer = Customer.builder()
                        .firstName(userRequestRegister.getFirstName())
                        .middleName(userRequestRegister.getMiddleName())
                        .lastName(userRequestRegister.getLastName())
                        .address(userRequestRegister.getAddress())
                        .phone(userRequestRegister.getPhone())
                        .user(user)
                        .build();
                user.setCustomer(customer);
                break;
            case ROLE_DISTRIBUTOR:
                break;
            case ROLE_MANUFACTURER:
                break;
            default:
                break;
        }

        this.userRepository.insert(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole().getDisplayName())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public Boolean confirm(String username) {
        User user = this.userRepository.getByUsername(username);
        if (user != null && !user.getIsConfirm()) {
            user.setIsConfirm(true);
            this.userRepository.update(user);

            return true;
        }

        return false;
    }

    @Override
    public UserResponse profile(String username) {
        User user = this.userRepository.getByUsername(username);

        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole().getDisplayName())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public UserResponse updateProfile(String username, UserRequestUpdate userRequestUpdate) {
        User user = this.userRepository.getByUsername(username);

        if (!user.getIsConfirm()) {
            throw new UserException("Tài khoản chưa được xác nhận");
        }

        if (userRequestUpdate.getAvatar() != null && !userRequestUpdate.getAvatar().isEmpty()) {
            String avatarUrl = this.cloudinaryService.uploadImage(userRequestUpdate.getAvatar());
            user.setAvatar(avatarUrl);
        }

        if (userRequestUpdate.getEmail() != null && !userRequestUpdate.getEmail().isEmpty()) {
            user.setEmail(userRequestUpdate.getEmail());
        }

        if (userRequestUpdate.getUsername() != null && !userRequestUpdate.getUsername().isEmpty()) {
            user.setUsername(userRequestUpdate.getUsername());
        }

        if (userRequestUpdate.getPassword() != null && !userRequestUpdate.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequestUpdate.getPassword()));
        }

        this.userRepository.update(user);

        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole().getDisplayName())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public UserResponse getUserResponse(Long id) {
        User user = this.userRepository.get(id);

        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole().getDisplayName())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public User get(Long id) {
        return this.userRepository.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return this.userRepository.getByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.getByEmail(email);
    }

    @Override
    public void insert(User user) {
        this.userRepository.insert(user);
    }

    @Override
    public void updateProfile(User user) {
        this.userRepository.update(user);
    }

    @Override
    public void insertOrUpdate(User user) {
        this.userRepository.insertOrUpdate(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.userRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.userRepository.count();
    }

    @Override
    public Boolean exists(Long id) {
        return this.userRepository.exists(id);
    }

    @Override
    public List<UserResponse> getAll(Map<String, String> params) {
        List<User> users = this.userRepository.getAll(params);

        return users.stream().map(user -> UserResponse.builder()
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .avatar(user.getAvatar())
                        .role(user.getUserRole().getDisplayName())
                        .isConfirm(user.getIsConfirm())
                        .lastLogin(user.getLastLogin())
                        .build())
                .collect(Collectors.toList());
    }
}
