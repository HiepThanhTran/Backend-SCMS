package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.customer.CustomerDTO;
import com.fh.scms.pojo.Customer;
import com.fh.scms.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/customers", produces = "application/json; charset=UTF-8")
public class APICustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> listCustomer(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Customer> customers = this.customerService.findAllWithFilter(params);

        return ResponseEntity.ok(customers);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable(value = "customerId") Long id) {
        Customer customer = this.customerService.findById(id);
        if (Optional.ofNullable(customer).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CustomerDTO customerDTO = this.customerService.getCustomerResponse(customer);
        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfileCustomer(Principal principal) {
        Customer customer = this.customerService.getProfileCustomer(principal.getName());

        return ResponseEntity.ok(customer);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileCustomer(Principal principal, @ModelAttribute @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            CustomerDTO updatedCustomerDTO = this.customerService.updateProfileCustomer(principal.getName(), customerDTO);

            return ResponseEntity.ok(updatedCustomerDTO);
        } catch (IllegalArgumentException e) {
            List<MessageResponse> errorMessages = List.of(new MessageResponse(e.getMessage()));

            return ResponseEntity.badRequest().body(errorMessages);
        }
    }
}
