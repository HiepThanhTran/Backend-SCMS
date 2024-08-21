package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Category;
import com.fh.scm.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategory(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("categories", categoryService.getAll(params));

        return "categories";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addCategory(HttpServletRequest request, Model model, @ModelAttribute(value = "category") @Valid Category category,
            BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_category";
            }

            categoryService.insert(category);

            return "redirect:/admin/categories";
        }

        return "add_category";
    }

    @RequestMapping(path = "/edit/{categoryId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editCategory(HttpServletRequest request, Model model, @PathVariable(value = "categoryId") Long id,
            @ModelAttribute(value = "category") @Valid Category category, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_category";
            }

            categoryService.update(category);

            return "redirect:/admin/categories";
        }

        model.addAttribute("category", categoryService.get(id));

        return "edit_category";
    }

    @DeleteMapping(path = "/delete/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value = "categoryId") Long id) {
        categoryService.delete(id);
    }

    @DeleteMapping(path = "/hide/{categoryId}")
    public String hideCategory(@PathVariable(value = "categoryId") Long id) {
        categoryService.softDelete(id);

        return "redirect:/admin/categories";
    }
}
