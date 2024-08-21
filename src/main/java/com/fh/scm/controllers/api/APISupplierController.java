package com.fh.scm.controllers.api;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.dto.api.rating.RatingRequestCreate;
import com.fh.scm.dto.api.payment_temrs.PaymentTermsRequest;
import com.fh.scm.dto.api.supplier.SupplierDTO;
import com.fh.scm.exceptions.RatingSupplierException;
import com.fh.scm.exceptions.UserException;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.pojo.Rating;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import com.fh.scm.services.RatingService;
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
    private final RatingService ratingService;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Supplier> suppliers = this.supplierService.getAll(params);

        return ResponseEntity.ok(suppliers);
    }

    @GetMapping(path = "/{supplierId}/retrieve")
    public ResponseEntity<?> details(HttpServletRequest request, @PathVariable(value = "supplierId") Long id) {
        Supplier supplier = this.supplierService.get(id);

        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        SupplierDTO supplierDTO = this.supplierService.getSupplierResponse(supplier);

        return ResponseEntity.ok(supplierDTO);
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> profileSupplier(Principal principal) {
        Supplier supplier = this.supplierService.getProfileSupplier(principal.getName());

        return ResponseEntity.ok(supplier);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileSupplier(Principal principal, @ModelAttribute @Valid SupplierDTO supplierDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            SupplierDTO updatedSupplierDTO = this.supplierService.updateProfileSupplier(principal.getName(), supplierDTO);

            return ResponseEntity.ok(updatedSupplierDTO);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping(path = "/{supplierId}/rating")
    public ResponseEntity<?> getAllRatings(@PathVariable(value = "supplierId") Long supplierId, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        params.put("supplierId", supplierId.toString());
        List<Rating> allRatingsOfSupplier = this.ratingService.getAll(params);

        return ResponseEntity.ok(allRatingsOfSupplier);
    }

    @PostMapping(path = "/{supplierId}/rating/add")
    public ResponseEntity<?> addRatingSupplier(Principal principal, @PathVariable(value = "supplierId") Long supplierId,
                                               @ModelAttribute @Valid RatingRequestCreate ratingRequestCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ResponseMessage> errorMessages = ResponseMessage.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            Rating rating = this.supplierService.addRatingForSupplier(principal.getName(), supplierId, ratingRequestCreate);

            return ResponseEntity.ok(rating);
        } catch (RatingSupplierException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }
}
