package com.fh.scm.services.implement;

import com.fh.scm.dto.api.payment_temrs.PaymentTermsRequest;
import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.enums.Color;
import com.fh.scm.enums.PaymentTermType;
import com.fh.scm.enums.UserRole;
import com.fh.scm.pojo.User;
import com.fh.scm.services.UserService;
import com.fh.scm.services._InitializerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class _InitializerDataServiceImplement implements _InitializerDataService {

    @Autowired
    private UserService userService;

    @Override
    public void createUser() {
        List<User> users = this.userService.getAll(null);

        if (users.isEmpty()) {
            this.userService.register(UserRequestRegister.builder()
                    .email("admin@scm.com")
                    .username("adminscm")
                    .password("admin@123")
                    .userRole(UserRole.ROLE_ADMIN)
                    .build());
            System.out.println(Color.BLUE_BOLD + "Create admin success" + Color.BLUE_BOLD);

            this.userService.register(UserRequestRegister.builder()
                    .email("customer1@scm.com")
                    .username("customer1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_CUSTOMER)
                    .firstName("CUSTOMER 1")
                    .middleName("M")
                    .lastName("L")
                    .address("TPHCM")
                    .phone("0123456789")
                    .build());
            System.out.println(Color.BLUE_BOLD + "Create customer success" + Color.BLUE_BOLD);

            Set<PaymentTermsRequest> termsRequestSet = Set.of(
                    PaymentTermsRequest.builder()
                            .discountDays(10)
                            .discountPercentage(0.03f)
                            .type(PaymentTermType.COD)
                            .build(),
                    PaymentTermsRequest.builder()
                            .discountDays(20)
                            .discountPercentage(0.05f)
                            .type(PaymentTermType.PREPAID)
                            .build()
            );
            this.userService.register(UserRequestRegister.builder()
                    .email("supplier1@scm.com")
                    .username("supplier1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SUPPLIER)
                    .name("SUPPLIER 1")
                    .address("TPHCM")
                    .phone("1234567890")
                    .contactInfo("1234567890")
                    .paymentTermsSet(termsRequestSet)
                    .build());
            System.out.println(Color.BLUE_BOLD + "Create supplier success" + Color.BLUE_BOLD);

            this.userService.register(UserRequestRegister.builder()
                    .email("shipper1@scm.com")
                    .username("shipper1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SHIPPER)
                    .name("SHIPPER 1")
                    .contactInfo("0987654321")
                    .build());
            System.out.println(Color.BLUE_BOLD + "Create supplier success" + Color.BLUE_BOLD);
        }
    }
}
