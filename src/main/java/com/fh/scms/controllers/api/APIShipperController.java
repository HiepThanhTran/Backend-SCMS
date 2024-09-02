package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.shipper.ShipperDTO;
import com.fh.scms.pojo.Shipper;
import com.fh.scms.services.ShipperService;
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
@RequestMapping(path = "/api/shippers", produces = "application/json; charset=UTF-8")
public class APIShipperController {

    private final ShipperService shipperService;

    @GetMapping
    public ResponseEntity<?> listShipper(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Shipper> shippers = this.shipperService.findAllWithFilter(params);

        return ResponseEntity.ok(shippers);
    }

    @GetMapping(path = "/{shipperId}")
    public ResponseEntity<?> getShipper(@PathVariable(value = "shipperId") Long id) {
        Shipper shipper = this.shipperService.findById(id);
        Optional.ofNullable(shipper).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng"));

        ShipperDTO shipperDTO = this.shipperService.getShipperResponse(shipper);

        return ResponseEntity.ok(shipperDTO);
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfileShipper(Principal principal) {
        Shipper shipper = this.shipperService.getProfileShipper(principal.getName());

        return ResponseEntity.ok(shipper);
    }

    @PostMapping(path = "/profile/update")
    public ResponseEntity<?> updateProfileShipper(Principal principal, @ModelAttribute @Valid ShipperDTO shipperDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(MessageResponse.fromBindingResult(bindingResult));
        }

        ShipperDTO updatedShipperDTO = this.shipperService.updateProfileShipper(principal.getName(), shipperDTO);

        return ResponseEntity.ok(updatedShipperDTO);
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
