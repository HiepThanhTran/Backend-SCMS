package com.fh.scm.controllers.api;

import com.fh.scm.dto.tax.TaxResponse;
import com.fh.scm.services.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/taxs", produces = "application/json; charset=UTF-8")
public class APITaxController {

    private final TaxService taxService;

    @GetMapping
    public ResponseEntity<?> getTaxs(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<TaxResponse> taxs = this.taxService.getAllTaxResponse(params);

        return ResponseEntity.ok(taxs);
    }
}
