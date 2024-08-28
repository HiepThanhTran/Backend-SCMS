package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/suppliers", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> listSuppliers(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Supplier> suppliers = this.supplierService.findAllWithFilter(params);

        return ResponseEntity.ok(suppliers);
    }

    @GetMapping(path = "/{supplierId}")
    public ResponseEntity<?> getSupplier(@PathVariable(value = "supplierId") Long id) {
        Supplier supplier = this.supplierService.findById(id);
        if (Optional.ofNullable(supplier).isEmpty()) {
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
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        try {
            SupplierDTO updatedSupplierDTO = this.supplierService.updateProfileSupplier(principal.getName(), supplierDTO);

            return ResponseEntity.ok(updatedSupplierDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
        }
    }

    @GetMapping(path = "/{supplierId}/ratings")
    public ResponseEntity<?> getRatingsForSupplier(@PathVariable(value = "supplierId") Long supplierId) {
        List<Rating> ratings = this.supplierService.getRatingsForSupplier(supplierId);

        return ResponseEntity.ok(ratings);
    }

    @PostMapping(path = "/{supplierId}/rating/add")
    public ResponseEntity<?> addRatingForSupplier(Principal principal, @PathVariable(value = "supplierId") Long supplierId,
                                                  @ModelAttribute @Valid RatingRequestCreate ratingRequestCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        try {
            Rating rating = this.supplierService.addRatingForSupplier(principal.getName(), supplierId, ratingRequestCreate);

            return ResponseEntity.ok(rating);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.badRequest().body(List.of(new MessageResponse(e.getMessage())));
        }
    }
}
