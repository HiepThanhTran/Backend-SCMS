package com.fh.scm.controllers;

import com.fh.scm.dto.error.ErrorResponse;
import com.fh.scm.pojo.SupplierCosting;
import com.fh.scm.services.SupplierCostingService;
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
@RequestMapping(path = "/admin/supplier-costings")
public class SupplierCostingController {

    private final SupplierCostingService supplierCostingService;

    @GetMapping
    public String listSupplierCosting(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("supplierCostings", supplierCostingService.getAll(params));

        return "supplier_costings";
    }

    @GetMapping(path = "/{supplierCostingId}")
    public String retrieveSupplierCosting(@PathVariable(value = "supplierCostingId") Long id, Model model) {
        model.addAttribute("supplierCosting", supplierCostingService.get(id));

        return "supplier_costing";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addSupplierCosting(HttpServletRequest request, Model model, @ModelAttribute(value = "supplierCosting") @Valid SupplierCosting supplierCosting,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_supplier_costing";
            }

            supplierCostingService.insert(supplierCosting);

            return "redirect:/admin/supplier-costings";
        }

        return "add_supplier_costing";
    }

    @RequestMapping(path = "/edit/{supplierCostingId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editSupplierCosting(HttpServletRequest request, Model model, @PathVariable(value = "supplierCostingId") Long id,
                          @ModelAttribute(value = "supplierCosting") @Valid SupplierCosting supplierCosting, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_supplier_costing";
            }

            supplierCostingService.update(supplierCosting);

            return "redirect:/admin/supplier-costings";
        }

        model.addAttribute("supplierCosting", supplierCostingService.get(id));

        return "edit_supplier_costing";
    }

    @DeleteMapping(path = "/delete/{supplierCostingId}")
    public String deleteSupplierCosting(@PathVariable(value = "supplierCostingId") Long id) {
        supplierCostingService.delete(id);

        return "redirect:/admin/supplier-costings";
    }

    @DeleteMapping(path = "/hide/{supplierCostingId}")
    public String hideSupplierCosting(@PathVariable(value = "supplierCostingId") Long id) {
        supplierCostingService.softDelete(id);

        return "redirect:/admin/supplier-costings";
    }
}
