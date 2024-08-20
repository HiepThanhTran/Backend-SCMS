package com.fh.scm.services.implement;

import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.dto.api.user.UserRequestUpdate;
import com.fh.scm.dto.api.user.UserResponse;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.Customer;
import com.fh.scm.pojo.Shipper;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.CustomerRepository;
import com.fh.scm.repository.ShipperRepository;
import com.fh.scm.repository.SupplierRepository;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.services._CloudinaryService;
import com.fh.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private _CloudinaryService cloudinaryService;

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
    public UserResponse getUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole().getDisplayName())
                .isConfirm(user.getIsConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public List<UserResponse> getAllUserResponse(Map<String, String> params) {
        List<User> users = this.userRepository.getAll(params);

        return users.stream().map(this::getUserResponse).collect(Collectors.toList());
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

        user = this.userRepository.getByEmail(userRequestRegister.getEmail());
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
                        .user(user)
                        .build();
                user.setSupplier(supplier);
                break;
            case ROLE_DISTRIBUTOR:
                break;
            case ROLE_MANUFACTURER:
                break;
            case ROLE_SHIPPER:
                Shipper shipper = Shipper.builder()
                        .name(userRequestRegister.getName())
                        .contactInfo(userRequestRegister.getContactInfo())
                        .user(user)
                        .build();
                user.setShipper(shipper);
                break;
            default:
                break;
        }

        this.userRepository.insert(user);

        return this.getUserResponse(user);
    }

    @Override
    public Boolean confirmUser(String username) {
        User user = this.userRepository.getByUsername(username);
        if (user != null && !user.getIsConfirm()) {
            user.setIsConfirm(true);
            this.userRepository.update(user);

            return true;
        }

        return false;
    }

    @Override
    public UserResponse getProfileUser(String username) {
        User user = this.userRepository.getByUsername(username);

        return this.getUserResponse(user);
    }

    @Override
    public UserResponse updateProfileUser(String username, UserRequestUpdate userRequestUpdate) {
        User user = this.userRepository.getByUsername(username);

        if (!user.getIsConfirm()) {
            throw new UserException("Tài khoản chưa được xác nhận");
        }

        Field[] fields = UserRequestUpdate.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(userRequestUpdate);

                if (value != null && !value.toString().isEmpty()) {
                    Field userField = User.class.getDeclaredField(field.getName());
                    userField.setAccessible(true);

                    if (field.getName().equals("avatar")) {
                        String avatarUrl = this.cloudinaryService.uploadImage((MultipartFile) value);
                        userField.set(user, avatarUrl);
                    } else if (field.getName().equals("password")) {
                        userField.set(user, passwordEncoder.encode(value.toString()));
                    } else {
                        userField.set(user, value);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        this.userRepository.update(user);

        return this.getUserResponse(user);
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
    public void updateProfileUser(User user) {
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
    public List<User> getAll(Map<String, String> params) {
        return this.userRepository.getAll(params);
    }
}
