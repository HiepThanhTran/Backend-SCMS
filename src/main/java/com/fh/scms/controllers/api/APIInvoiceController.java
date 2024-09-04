package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.invoice.ChargeRequest;
import com.fh.scms.dto.invoice.InvoiceResponse;
import com.fh.scms.pojo.User;
import com.fh.scms.services.InvoiceService;
import com.fh.scms.services.UserService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoices", produces = "application/json; charset=UTF-8")
public class APIInvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> listInvoices(Principal principal, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        User user = this.userService.findByUsername(principal.getName());
        Optional.ofNullable(user).orElseThrow(() -> new EntityNotFoundException("không tìm thấy người dùng"));

        params.put("userId", user.getId().toString());
        List<InvoiceResponse> invoiceList = this.invoiceService.getAllInvoiceResponse(params);

        return ResponseEntity.ok(invoiceList);
    }

    @GetMapping(path = "/{invoiceNumber}")
    public ResponseEntity<?> findInvoiceByInvoiceNumber(@PathVariable String invoiceNumber) {
        InvoiceResponse invoice = this.invoiceService.getInvoiceResponse(this.invoiceService.findByInvoiceNumber(invoiceNumber));

        return ResponseEntity.ok(invoice);
    }

    @PostMapping(path = "/charge")
    public ResponseEntity<?> charge(@RequestBody @Valid ChargeRequest chargeRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        Stripe.apiKey = "sk_test_51O41qGBy1BulLKF8u3JAYr4tDLkAXiR3UpjDbyJn3QR5Jfr84h62AVXmU9RPKgwjtIIUSKPKIlvsOZJeTUHfdzZu0013X6szi0";

        Map<String, String> response = new HashMap<>();
        try {
            PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                    .setAmount(Long.parseLong(String.valueOf(chargeRequest.getAmount())))
                    .setCurrency("vnd")
                    .addPaymentMethodType("card")
                    .setReceiptEmail(chargeRequest.getCustomer().getCustomerEmail())
                    .setDescription("Hóa đơn thanh toán đơn hàng của khách hàng từ SCMS")
                    .putAllMetadata(Map.of(
                            "customer_name", chargeRequest.getCustomer().getCustomerName(),
                            "customer_email", chargeRequest.getCustomer().getCustomerEmail(),
                            "customer_phone", chargeRequest.getCustomer().getCustomerPhone(),
                            "customer_address", chargeRequest.getCustomer().getCustomerAddress()
                    ))
                    .putMetadata("products", chargeRequest.getProducts().stream()
                            .map(product -> String.format("ID: %s, Name: %s, Price: %s",
                                    product.getId(), product.getName(), product.getPrice().toString()))
                            .collect(Collectors.joining("\n"))
                    )
                    .setPaymentMethodOptions(PaymentIntentCreateParams.PaymentMethodOptions.builder()
                            .setCard(PaymentIntentCreateParams.PaymentMethodOptions.Card.builder()
                                    .setRequestThreeDSecure(PaymentIntentCreateParams.PaymentMethodOptions.Card.RequestThreeDSecure.AUTOMATIC)
                                    .build())
                            .build())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
            response.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(@NotNull HttpServletRequest req, AccessDeniedException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(@NotNull HttpServletRequest req, Exception e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
    }
}
