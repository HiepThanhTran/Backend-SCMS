package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Inventory;
import com.fh.scm.services.InventoryService;
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
@RequestMapping(path = "/admin/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public String listInventory(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("inventories", inventoryService.getAll(params));

        return "inventories";
    }
    
    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addInventory(HttpServletRequest request, Model model, @ModelAttribute(value = "inventory") @Valid Inventory inventory,
                               BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_inventory";
            }

            inventoryService.insert(inventory);

            return "redirect:/admin/inventories";
        }

        return "add_inventory";
    }

    @RequestMapping(path = "/edit/{inventoryId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editInventory(HttpServletRequest request, Model model, @PathVariable(value = "inventoryId") Long id,
                                @ModelAttribute(value = "inventory") @Valid Inventory inventory, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_inventory";
            }

            inventoryService.update(inventory);

            return "redirect:/admin/inventories";
        }

        model.addAttribute("inventory", inventoryService.get(id));

        return "edit_inventory";
    }

    @DeleteMapping(path = "/delete/{inventoryId}")
    public String deleteInventory(@PathVariable(value = "inventoryId") Long id) {
        inventoryService.delete(id);

        return "redirect:/admin/inventories";
    }

    @DeleteMapping(path = "/hide/{inventoryId}")
    public String hideInventory(@PathVariable(value = "inventoryId") Long id) {
        inventoryService.softDelete(id);

        return "redirect:/admin/inventories";
    }
}
