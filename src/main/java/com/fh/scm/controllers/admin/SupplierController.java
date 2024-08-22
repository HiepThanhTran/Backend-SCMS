package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.Supplier;
import com.fh.scm.services.SupplierService;
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
@RequestMapping(path = "/admin/suppliers", produces = "application/json; charset=UTF-8")
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping
    public String listSuppliers(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("suppliers", supplierService.getAll(params));

        return "suppliers";
    }

    @GetMapping(path = "/{supplierId}")
    public String retrieveSupplier(@PathVariable(value = "supplierId") Long id, Model model) {
        model.addAttribute("supplier", supplierService.get(id));

        return "supplier";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addSupplier(HttpServletRequest request, Model model, @ModelAttribute(value = "supplier") @Valid Supplier supplier,
                              BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_supplier";
            }

            supplierService.insert(supplier);

            return "redirect:/admin/suppliers";
        }

        return "add_supplier";
    }

    @RequestMapping(path = "/edit/{supplierId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editSupplier(HttpServletRequest request, Model model, @PathVariable(value = "supplierId") Long id,
                               @ModelAttribute(value = "supplier") @Valid Supplier supplier, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_supplier";
            }

            supplierService.update(supplier);

            return "redirect:/admin/suppliers";
        }

        return "edit_supplier";
    }

    @DeleteMapping(path = "/delete/{supplierId}")
    public String deleteSupplier(@PathVariable(value = "supplierId") Long id) {
        supplierService.delete(id);

        return "redirect:/admin/suppliers";
    }

    @DeleteMapping(path = "/hide/{supplierId}")
    public String hideSupplier(@PathVariable(value = "supplierId") Long id) {
        supplierService.softDelete(id);

        return "redirect:/admin/suppliers";
    }
}
