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

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute("categories", this.categoryService.getAll(params));

        return "categories";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addCategory(HttpServletRequest request, Model model, @ModelAttribute(value = "category") @Valid Category category,
                              BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_category";
            }

            this.categoryService.insert(category);

            return "redirect:/admin/categories";
        }

        return "add_category";
    }

    @RequestMapping(path = "/edit/{categoryId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editCategory(HttpServletRequest request, Model model, @PathVariable(value = "categoryId") Long id,
                               @ModelAttribute(value = "category") @Valid Category category, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_category";
            }

            this.categoryService.update(category);

            return "redirect:/admin/categories";
        }

        model.addAttribute("category", this.categoryService.get(id));

        return "edit_category";
    }

    @DeleteMapping(path = "/delete/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCategory(@PathVariable(value = "categoryId") Long id) {
        categoryService.delete(id);

        return "redirect:/admin/categories";
    }
}
