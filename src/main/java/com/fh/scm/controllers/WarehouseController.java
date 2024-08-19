package com.fh.scm.controllers;

import com.fh.scm.dto.error.ErrorResponse;
import com.fh.scm.pojo.Warehouse;
import com.fh.scm.services.WarehouseService;
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
@RequestMapping(path = "/admin/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public String listWarehouse(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("warehouses", warehouseService.getAll(params));

        return "warehouses";
    }

    @GetMapping(path = "/{warehouseId}")
    public String retrieveWarehouse(@PathVariable(value = "warehouseId") Long id, Model model) {
        model.addAttribute("warehouse", warehouseService.get(id));

        return "warehouse";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addWarehouse(HttpServletRequest request, Model model, @ModelAttribute(value = "warehouse") @Valid Warehouse warehouse,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_warehouse";
            }

            warehouseService.insert(warehouse);

            return "redirect:/admin/warehouses";
        }

        return "add_warehouse";
    }

    @RequestMapping(path = "/edit/{warehouseId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editWarehouse(HttpServletRequest request, Model model, @PathVariable(value = "warehouseId") Long id,
                          @ModelAttribute(value = "warehouse") @Valid Warehouse warehouse, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_warehouse";
            }

            warehouseService.update(warehouse);

            return "redirect:/admin/warehouses";
        }

        model.addAttribute("warehouse", warehouseService.get(id));

        return "edit_warehouse";
    }

    @DeleteMapping(path = "/delete/{warehouseId}")
    public String deleteWarehouse(@PathVariable(value = "warehouseId") Long id) {
        warehouseService.delete(id);

        return "redirect:/admin/warehouses";
    }

    @DeleteMapping(path = "/hide/{warehouseId}")
    public String hideWarehouse(@PathVariable(value = "warehouseId") Long id) {
        warehouseService.softDelete(id);

        return "redirect:/admin/warehouses";
    }
}
