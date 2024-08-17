package com.fh.scm.services.implement;

import com.fh.scm.enums.Role;
import com.fh.scm.pojo.User;
import com.fh.scm.repository.UserRepository;
import com.fh.scm.services.InitializerService;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InitializerServiceImplement implements InitializerService {

    private final Environment environment;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createAdmin() {
        Map<String, String> params = Map.of("role", Role.ADMIN.name());
        Optional<User> adminOpt = this.userService.getAll(params).stream().findFirst();

        adminOpt.orElseGet(() -> {
            String defaultAdminEmail = environment.getProperty("app.admin.default.email");
            String defaultAdminUsername = environment.getProperty("app.admin.default.username");
            String defaultAdminPassword = environment.getProperty("app.admin.default.password");
            String hashedPassword = this.passwordEncoder.encode(defaultAdminPassword);
            User admin = User.builder()
                    .email(defaultAdminEmail)
                    .username(defaultAdminUsername)
                    .password(hashedPassword)
                    .isConfirm(true)
                    .role(Role.ADMIN)
                    .build();

            this.userService.insert(admin);
            return admin;
        });
    }
}
