package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.exceptions.RatingSupplierException;
import com.fh.scms.exceptions.UserException;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.services.SupplierService;
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

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/suppliers", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Supplier> suppliers = this.supplierService.getAll(params);

        return ResponseEntity.ok(suppliers);
    }

    @GetMapping(path = "/{supplierId}")
    public ResponseEntity<?> getSupplier(HttpServletRequest request, @PathVariable(value = "supplierId") Long id) {
        Supplier supplier = this.supplierService.get(id);

        if (supplier == null) {
            return ResponseEntity.notFound().build();
        }

        SupplierDTO supplierDTO = this.supplierService.getSupplierResponse(supplier);

        return ResponseEntity.ok(supplierDTO);
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfileSupplier(Principal principal) {
        Supplier supplier = this.supplierService.getProfileSupplier(principal.getName());

        return ResponseEntity.ok(supplier);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileSupplier(Principal principal, @ModelAttribute @Valid SupplierDTO supplierDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            SupplierDTO updatedSupplierDTO = this.supplierService.updateProfileSupplier(principal.getName(), supplierDTO);

            return ResponseEntity.ok(updatedSupplierDTO);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/{supplierId}/rating/add")
    public ResponseEntity<?> addRatingForSupplier(Principal principal, @PathVariable(value = "supplierId") Long supplierId,
                                                  @ModelAttribute @Valid RatingRequestCreate ratingRequestCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errorMessages = MessageResponse.fromBindingResult(bindingResult);

            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            Rating rating = this.supplierService.addRatingForSupplier(principal.getName(), supplierId, ratingRequestCreate);

            return ResponseEntity.ok(rating);
        } catch (RatingSupplierException e) {
            if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
