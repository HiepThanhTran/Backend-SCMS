package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Category;
import com.fh.scms.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories", produces = "application/json; charset=UTF-8")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategory(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("categories", this.categoryService.findAllWithFilter(params));

        return "categories";
    }

    @GetMapping(path = "/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());

        return "add_category";
    }

    @PostMapping(path = "/add")
    public String addCategory(Model model, @ModelAttribute(value = "category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResult));

            return "add_category";
        }

        this.categoryService.save(category);

        return "redirect:/admin/categories";
    }

    @GetMapping(path = "/edit/{categoryId}")
    public String editCategory(Model model, @PathVariable(value = "categoryId") Long id) {
        model.addAttribute("category", this.categoryService.findById(id));

        return "edit_category";
    }

    @PostMapping(path = "/edit/{categoryId}")
    public String editCategory(Model model, @PathVariable(value = "categoryId") Long id,
                               @ModelAttribute(value = "category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResult));

            return "edit_category";
        }

        this.categoryService.update(category);

        return "redirect:/admin/categories";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{categoryId}")
    public void deleteCategory(@PathVariable(value = "categoryId") Long id) {
        categoryService.delete(id);
    }
}
