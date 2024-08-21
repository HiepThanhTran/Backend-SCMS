package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Product;
import com.fh.scm.services.CategoryService;
import com.fh.scm.services.ProductService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    @GetMapping
    public String listProducts(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("products", productService.getAll(params));

        return "products";
    }

    @GetMapping(path = "/{productId")
    public String getProduct(Model model, @RequestParam Long productId) {
        model.addAttribute("product", productService.get(productId));

        return "product";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addProduct(HttpServletRequest request, Model model, @ModelAttribute(value = "product") @Valid Product product,
                             BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_product";
            }

            productService.insert(product);

            return "redirect:/admin/products";
        }

        model.addAttribute("categories", categoryService.getAll(null));
        return "add_product";
    }

    @RequestMapping(path = "/edit/{productId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editProduct(HttpServletRequest request, Model model, @PathVariable(value = "productId") Long id,
                              @ModelAttribute(value = "product") @Valid Product product, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_product";
            }

            productService.update(product);

            return "redirect:/admin/products";
        }

        model.addAttribute("product", productService.get(id));

        return "edit_product";
    }

    @DeleteMapping(path = "/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") Long id) {
        productService.delete(id);

        return "redirect:/admin/products";
    }

    @DeleteMapping(path = "/hide/{productId}")
    public String hideProduct(@PathVariable(value = "productId") Long id) {
        productService.softDelete(id);

        return "redirect:/admin/products";
    }
}
