package com.fh.scms.controllers.api;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Product;
import com.fh.scms.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/products", produces = "application/json; charset=UTF-8")
public class APIProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> listProducts(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<Product> products = this.productService.findAllWithFilter(params);

        return ResponseEntity.ok(this.productService.getAllProductResponseForList(products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(value = "productId") Long productId) {
        Product product = this.productService.findById(productId);
        Optional.ofNullable(product).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm"));

        return ResponseEntity.ok(this.productService.getProductResponseForDetails(product));
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
