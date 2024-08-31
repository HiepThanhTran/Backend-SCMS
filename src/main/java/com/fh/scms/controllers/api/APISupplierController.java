package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.rating.RatingRequestCreate;
import com.fh.scms.dto.supplier.SupplierDTO;
import com.fh.scms.pojo.Rating;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.services.SupplierService;
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
        Optional.ofNullable(supplier).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy nhà cung cấp"));

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

        SupplierDTO updatedSupplierDTO = this.supplierService.updateProfileSupplier(principal.getName(), supplierDTO);

        return ResponseEntity.ok(updatedSupplierDTO);
    }

    @GetMapping(path = "/{supplierId}/ratings")
    public ResponseEntity<?> getRatingsOfSupplier(@PathVariable(value = "supplierId") Long supplierId) {
        List<Rating> ratings = this.supplierService.getRatingsOfSupplier(supplierId);

        return ResponseEntity.ok(ratings);
    }

    @PostMapping(path = "/{supplierId}/rating/add")
    public ResponseEntity<?> addRatingForSupplier(Principal principal, @PathVariable(value = "supplierId") Long supplierId,
                                                  @ModelAttribute @Valid RatingRequestCreate ratingRequestCreate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        Rating rating = this.supplierService.addRatingForSupplier(principal.getName(), supplierId, ratingRequestCreate);

        return ResponseEntity.ok(rating);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(@NotNull HttpServletRequest req, EntityNotFoundException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of(new MessageResponse(e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<?> handleIllegalArgumentException(@NotNull HttpServletRequest req, IllegalArgumentException e) {
        LoggerFactory.getLogger(req.getRequestURI()).error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(new MessageResponse(e.getMessage())));
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
