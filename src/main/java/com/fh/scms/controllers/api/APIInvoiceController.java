package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.invoice.InvoiceResponse;
import com.fh.scms.pojo.User;
import com.fh.scms.services.InvoiceService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        params.put("userId", user.getId().toString());
        List<InvoiceResponse> invoiceList = this.invoiceService.getAllInvoiceResponse(params);

        return ResponseEntity.ok(invoiceList);
    }

    @PostMapping(path = "/{invoiceId}/pay")
    public ResponseEntity<?> payInvoice(@PathVariable(value = "invoiceId") Long invoiceId) {
        try {
            this.invoiceService.payInvoice(invoiceId);

            return ResponseEntity.ok().build();
        } catch(EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof  EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
