package com.fh.scm.controllers.api;

import com.fh.scm.dto.api.category.CategoryResponse;
import com.fh.scm.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/categories", produces = "application/json; charset=UTF-8")
public class APICategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<CategoryResponse> categories = this.categoryService.getAllCategoryResponse(params);

        return ResponseEntity.ok(categories);
    }
}
