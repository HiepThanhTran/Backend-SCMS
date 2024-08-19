package com.fh.scm.controllers;

import com.fh.scm.dto.error.ErrorResponse;
import com.fh.scm.pojo.InventoryDetails;
import com.fh.scm.services.InventoryDetailsService;
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
@RequestMapping(path = "/admin/inventory-details")
public class InventoryDetailsController {

    private final InventoryDetailsService inventoryDetailService;

    @GetMapping
    public String listInventoryDetails(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("inventoryDetails", inventoryDetailService.getAll(params));

        return "inventory_details";
    }

    @GetMapping(path = "/{inventoryDetailId}")
    public String retrieveInventoryDetails(@PathVariable(value = "inventoryDetailId") Long id, Model model) {
        model.addAttribute("inventoryDetail", inventoryDetailService.get(id));

        return "inventory_detail";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addInventoryDetails(HttpServletRequest request, Model model, @ModelAttribute(value = "inventoryDetail") @Valid InventoryDetails inventoryDetail,
                                  BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_inventory_detail";
            }

            inventoryDetailService.insert(inventoryDetail);

            return "redirect:/admin/inventory-details";
        }

        return "add_inventory_detail";
    }

    @RequestMapping(path = "/edit/{inventoryDetailId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editInventoryDetails(HttpServletRequest request, Model model, @PathVariable(value = "inventoryDetailId") Long id,
                                   @ModelAttribute(value = "inventoryDetail") @Valid InventoryDetails inventoryDetail, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_inventory_detail";
            }

            inventoryDetailService.update(inventoryDetail);

            return "redirect:/admin/inventory-details";
        }

        model.addAttribute("inventoryDetail", inventoryDetailService.get(id));

        return "edit_inventory_detail";
    }

    @DeleteMapping(path = "/delete/{inventoryDetailId}")
    public String deleteInventoryDetails(@PathVariable(value = "inventoryDetailId") Long id) {
        inventoryDetailService.delete(id);

        return "redirect:/admin/inventory-details";
    }

    @DeleteMapping(path = "/hide/{inventoryDetailId}")
    public String hideInventoryDetails(@PathVariable(value = "inventoryDetailId") Long id) {
        inventoryDetailService.softDelete(id);

        return "redirect:/admin/inventory-details";
    }
}
