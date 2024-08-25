package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Product;
import com.fh.scms.services.CategoryService;
import com.fh.scms.services.ProductService;
import com.fh.scms.services.TagService;
import com.fh.scms.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/products", produces = "application/json; charset=UTF-8")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final UnitService unitService;

    @GetMapping
    public String listProducts(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("products", this.productService.findAllWithFilter(params));

        return "products";
    }

    @GetMapping(path = "/add")
    public String addProduct(Model model) {
        model.addAttribute("categories", this.categoryService.findAllWithFilter(null));
        model.addAttribute("tags", this.tagService.findAllWithFilter(null));
        model.addAttribute("units", this.unitService.findAllWithFilter(null));
        model.addAttribute("product", new Product());

        return "add_product";
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addProduct(Model model,
                             @RequestParam(value = "tagIds", required = false, defaultValue = "") List<String> tagIds,
                             @ModelAttribute(value = "product") @Valid Product product,
                             BindingResult bindingResult) {
        model.addAttribute("categories", this.categoryService.findAllWithFilter(null));
        model.addAttribute("tags", this.tagService.findAllWithFilter(null));
        model.addAttribute("units", this.unitService.findAllWithFilter(null));

        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "add_product";
        }

        this.productService.save(product, tagIds);

        return "redirect:/admin/products";
    }

    @GetMapping(path = "/edit/{productId}")
    public String editProduct(Model model, @PathVariable(value = "productId") Long id) {
        model.addAttribute("categories", this.categoryService.findAllWithFilter(null));
        model.addAttribute("tags", this.tagService.findAllWithFilter(null));
        model.addAttribute("units", this.unitService.findAllWithFilter(null));
        model.addAttribute("product", this.productService.findById(id));
        model.addAttribute("productTags", this.tagService.findByProductId(id));

        return "edit_product";
    }

    @PostMapping(path = "/edit/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String editProduct(Model model, @PathVariable(value = "productId") Long id,
                              @RequestParam(value = "tagIds", required = false, defaultValue = "") List<String> tagIds,
                              @ModelAttribute(value = "product") @Valid Product product,
                              BindingResult bindingResult) {
        model.addAttribute("categories", this.categoryService.findAllWithFilter(null));
        model.addAttribute("tags", this.tagService.findAllWithFilter(null));
        model.addAttribute("units", this.unitService.findAllWithFilter(null));

        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "edit_product";
        }

        this.productService.update(product, tagIds);

        return "redirect:/admin/products";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{productId}")
    public String deleteProduct(@PathVariable(value = "productId") Long id) {
        this.productService.delete(id);

        return "redirect:/admin/products";
    }
}
