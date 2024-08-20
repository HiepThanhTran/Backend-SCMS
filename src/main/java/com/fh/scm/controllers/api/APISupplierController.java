package com.fh.scm.controllers.api;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.dto.api.rating.RatingRequest;
import com.fh.scm.dto.api.supplier.PaymentTermsRequest;
import com.fh.scm.dto.api.supplier.SupplierDTO;
import com.fh.scm.exceptions.RatingSupplierException;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.services.SupplierService;
import com.fh.scm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/supplier", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierService supplierService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Supplier> suppliers = this.supplierService.getAll(params);

        return ResponseEntity.ok(suppliers);
    }

    @RequestMapping(path = "/{supplierId}/details", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> details(HttpServletRequest request, @PathVariable(value = "supplierId") Long id) {
        Supplier supplier = this.supplierService.get(id);

        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        if (request.getMethod().equals("GET")) {
            SupplierDTO supplierDTO = this.supplierService.getSupplierResponse(supplier);

            return ResponseEntity.ok(supplierDTO);
        }

        if (request.getMethod().equals("DELETE")) {
            this.supplierService.delete(id);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> profileSupplier(Principal principal) {
        Supplier supplierDTO = this.supplierService.getProfileSupplier(principal.getName());

        return ResponseEntity.ok(supplierDTO);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileSupplier(Principal principal, @ModelAttribute @Valid SupplierDTO supplierDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            System.out.println(principal.getName());
            SupplierDTO updatedSupplierDTO = this.supplierService.updateProfileSupplier(principal.getName(), supplierDTO);

            return ResponseEntity.ok(updatedSupplierDTO);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping(path = "/payment-terms")
    public ResponseEntity<?> getAllPaymentTerms(Principal principal) {
        List<PaymentTerms> paymentTerms = this.supplierService.getAllPaymentTermsOfSupplier(principal.getName());

        return ResponseEntity.ok(paymentTerms);
    }

    @PostMapping(path = "/payment-terms/add")
    public ResponseEntity<?> addPaymentTerms(Principal principal, @RequestBody PaymentTermsRequest paymentTermsId) {
        User user = this.userService.getByUsername(principal.getName());
        Supplier supplier = this.supplierService.getByUser(user);

        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        this.supplierService.addPaymentTermsForSupplier(supplier.getId(), paymentTermsId);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{supplierId}/rating")
    public ResponseEntity<?> getAllRatings(@PathVariable(value = "supplierId") Long supplierId) {
        Map<String, String> params = Map.of("supplierId", supplierId.toString());
        List<Rating> allRatingsOfSupplier = this.supplierService.getAllRatingsOfSupplier(params);

        return ResponseEntity.ok(allRatingsOfSupplier);
    }

    @PostMapping(path = "/{supplierId}/rating/add")
    public ResponseEntity<?> addRatingSupplier(Principal principal, HttpServletRequest request, @PathVariable(value = "supplierId") Long supplierId,
                                            @ModelAttribute @Valid RatingRequest rating, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            this.supplierService.addRatingForSupplier(principal.getName(), supplierId, rating);

            return ResponseEntity.ok().build();
        } catch (RatingSupplierException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }
}
