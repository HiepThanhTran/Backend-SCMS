package com.fh.scms.services.implement;

import com.fh.scms.components.GlobalService;
import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.dto.user.UserRequestUpdate;
import com.fh.scms.dto.user.UserResponse;
import com.fh.scms.pojo.*;
import com.fh.scms.repository.*;
import com.fh.scms.services.UserService;
import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service("userDetailsService")
@Transactional
public class UserServiceImplement implements UserService {

    @Autowired
    private GlobalService globalService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        Optional.ofNullable(user).orElseThrow(() -> new UsernameNotFoundException("Invalid User!"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void update(@NotNull User user) {
        if (user.getFile() != null && !user.getFile().isEmpty()) {
            user.setAvatar(this.globalService.uploadImage(user.getFile()));
        }

        this.userRepository.update(user);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(id);
    }

    @Override
    public Long count() {
        return this.userRepository.count();
    }

    @Override
    public List<User> findAllWithFilter(Map<String, String> params) {
        return this.userRepository.findAllWithFilter(params);
    }

    @Override
    public UserResponse getUserResponse(@NotNull User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .role(user.getUserRole())
                .isConfirm(user.getConfirm())
                .lastLogin(user.getLastLogin())
                .build();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        User user = this.userRepository.findByUsername(username);

        return user != null && this.passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void updateLastLogin(String username) {
        User user = this.userRepository.findByUsername(username);
        user.setLastLogin(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        this.userRepository.update(user);
    }

    @Override
    public UserResponse registerUser(@NotNull UserRequestRegister userRequestRegister) {
        User user = this.userRepository.findByUsername(userRequestRegister.getUsername());
        Optional.ofNullable(user).ifPresent(u -> {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        });

        user = this.userRepository.findByEmail(userRequestRegister.getEmail());
        Optional.ofNullable(user).ifPresent(u -> {
            throw new IllegalArgumentException("Email đã được liên kết đến tài khoản khác");
        });

        String avatarUrl = null;
        if (userRequestRegister.getAvatar() != null && !userRequestRegister.getAvatar().isEmpty()) {
            avatarUrl = this.globalService.uploadImage(userRequestRegister.getAvatar());
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
                user.setConfirm(true);
                break;
            case ROLE_CUSTOMER:
                Customer customer;
                customer = this.customerRepository.findByPhone(userRequestRegister.getPhone());
                Optional.ofNullable(customer).ifPresent(c -> {
                    throw new IllegalArgumentException("Số điện thoại đã được liên kết đến tài khoản khác");
                });

                customer = Customer.builder()
                        .firstName(userRequestRegister.getFirstName())
                        .middleName(userRequestRegister.getMiddleName())
                        .lastName(userRequestRegister.getLastName())
                        .address(userRequestRegister.getAddress())
                        .phone(userRequestRegister.getPhone())
                        .gender(userRequestRegister.getGender())
                        .dateOfBirth(userRequestRegister.getDateOfBirth())
                        .user(user)
                        .build();
                user.setCustomer(customer);
                break;
            case ROLE_SUPPLIER:
                Supplier supplier = this.supplierRepository.findByPhone(userRequestRegister.getPhone());
                Optional.ofNullable(supplier).ifPresent(s -> {
                    throw new IllegalArgumentException("Số điện thoại đã được liên kết đến tài khoản khác");
                });

                supplier = Supplier.builder()
                        .name(userRequestRegister.getName())
                        .address(userRequestRegister.getAddress())
                        .phone(userRequestRegister.getPhone())
                        .contactInfo(userRequestRegister.getContactInfo())
                        .user(user)
                        .build();

                final Supplier finalSupplier = supplier;
                Set<PaymentTerms> paymentTermsSet = Optional.ofNullable(userRequestRegister.getPaymentTermsSet())
                        .orElse(new HashSet<>())
                        .parallelStream()
                        .map(termsRequest -> PaymentTerms.builder()
                                .discountDays(termsRequest.getDiscountDays())
                                .discountPercentage(termsRequest.getDiscountPercentage())
                                .type(termsRequest.getType())
                                .supplier(finalSupplier)
                                .build()).collect(Collectors.toSet());
                supplier.setPaymentTermsSet(paymentTermsSet);

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

        this.userRepository.save(user);

        return this.getUserResponse(user);
    }

    @Override
    public Boolean confirmUser(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null && !user.getConfirm()) {
            user.setConfirm(true);
            this.userRepository.update(user);

            return true;
        }

        return false;
    }

    @Override
    public UserResponse getProfileUser(String username) {
        User user = this.userRepository.findByUsername(username);

        return this.getUserResponse(user);
    }

    @Override
    public UserResponse updateProfileUser(String username, UserRequestUpdate userRequestUpdate) {
        User user = this.userRepository.findByUsername(username);

        if (!user.getConfirm()) {
            throw new IllegalArgumentException("Tài khoản chưa được xác nhận");
        }

        if (userRequestUpdate.getOldPassword() != null && !userRequestUpdate.getOldPassword().isEmpty() &&
                userRequestUpdate.getNewPassword() != null && !userRequestUpdate.getNewPassword().isEmpty()) {
            if (!this.passwordEncoder.matches(userRequestUpdate.getOldPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Mật khẩu cũ không đúng");
            }

            user.setPassword(this.passwordEncoder.encode(userRequestUpdate.getNewPassword()));
        }

        Field[] fields = UserRequestUpdate.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.getName().equals("oldPassword") && !field.getName().equals("newPassword")) {
                try {
                    Object value = field.get(userRequestUpdate);

                    if (value != null && !value.toString().isEmpty()) {
                        Field userField = User.class.getDeclaredField(field.getName());
                        userField.setAccessible(true);

                        if (field.getName().equals("avatar")) {
                            String avatarUrl = this.globalService.uploadImage((MultipartFile) value);
                            userField.set(user, avatarUrl);
                        } else {
                            Object convertedValue = Utils.convertValue(userField.getType(), value.toString());
                            userField.set(user, convertedValue);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    Logger.getLogger(UserServiceImplement.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }

        this.userRepository.update(user);

        return this.getUserResponse(user);
    }
}
