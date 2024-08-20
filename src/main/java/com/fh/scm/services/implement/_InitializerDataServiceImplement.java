package com.fh.scm.services.implement;

import com.fh.scm.dto.api.user.UserRequestRegister;
import com.fh.scm.enums.Color;
import com.fh.scm.enums.PaymentTermType;
import com.fh.scm.enums.UserRole;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.User;
import com.fh.scm.services.PaymentTermsService;
import com.fh.scm.services.SupplierService;
import com.fh.scm.services.UserService;
import com.fh.scm.services._InitializerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class _InitializerDataServiceImplement implements _InitializerDataService {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTermsService paymentTermsService;
    @Autowired
    private SupplierService supplierService;

    @Override
    public void createPaymentTerms() {
        List<PaymentTerms> paymentTermsList = List.of(
                PaymentTerms.builder().discountDays(10).discountPercentage(1F).type(PaymentTermType.COD).build(),
                PaymentTerms.builder().discountDays(20).discountPercentage(1F).type(PaymentTermType.EOM).build(),
                PaymentTerms.builder().discountDays(20).discountPercentage(1F).type(PaymentTermType.PREPAID).build()
        );

        paymentTermsList.forEach(paymentTermsService::insert);
        System.out.println(Color.BLUE_BOLD + "Create payment terms success" + Color.BLUE_BOLD);
    }

    @Override
    public void createUser() {
        List<User> users = this.userService.getAll(null);

        if (users.isEmpty()) {
            UserRequestRegister adminRegister = UserRequestRegister.builder()
                    .email("admin@scm.com")
                    .username("adminscm")
                    .password("admin@123")
                    .userRole(UserRole.ROLE_ADMIN)
                    .build();

            this.userService.register(adminRegister);
            System.out.println(Color.BLUE_BOLD + "Create admin success" + Color.BLUE_BOLD);

            UserRequestRegister supplierRegister = UserRequestRegister.builder()
                    .email("supplier1@scm.com")
                    .username("supplier1")
                    .password("user@123")
                    .userRole(UserRole.ROLE_SUPPLIER)
                    .name("SUPPLIER1")
                    .address("TPHCM")
                    .phone("0123456789")
                    .contactInfo("0123456789")
                    .build();

            this.userService.register(supplierRegister);
            System.out.println(Color.BLUE_BOLD + "Create supplier success" + Color.BLUE_BOLD);
        }
    }
}
