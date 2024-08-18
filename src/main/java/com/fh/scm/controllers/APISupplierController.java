package com.fh.scm.controllers;

import com.fh.scm.dto.user.UserResponse;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/supplier", produces = "application/json; charset=UTF-8")
public class APISupplierController {

    @Autowired
    private SupplierService supplierService;

    public ResponseEntity<?> list(@RequestParam(required = false) Map<String, String> params) {
        List<Supplier> suppliers = this.supplierService.getAll(params);

        return ResponseEntity.ok(suppliers);
    }

    @RequestMapping(path = "/{supplierId}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public ResponseEntity<?> retrieve(HttpServletRequest request, @PathVariable(value = "supplierId") Long id) {
        if (!this.supplierService.exists(id)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getMethod().equals("GET")) {
        }

        if (request.getMethod().equals("DELETE")) {
        }

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}
