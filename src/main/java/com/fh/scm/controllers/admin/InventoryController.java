package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.Inventory;
import com.fh.scm.services.InventoryService;
import com.fh.scm.services.WarehouseService;
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
@RequestMapping(path = "/admin/inventories", produces = "application/json; charset=UTF-8")
public class InventoryController {

    private final InventoryService inventoryService;
    private final WarehouseService warehouseService;

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
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_inventory";
            }

            inventoryService.insert(inventory);
            return "redirect:/admin/inventories";
        }

        model.addAttribute("warehouses", warehouseService.getAll(null));
        return "add_inventory";
    }

    @RequestMapping(path = "/edit/{inventoryId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editInventory(HttpServletRequest request, Model model, @PathVariable(value = "inventoryId") Long id,
                                @ModelAttribute(value = "inventory") @Valid Inventory inventory, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_inventory";
            }

            inventoryService.update(inventory);

            return "redirect:/admin/inventories";
        }

        model.addAttribute("inventory", inventoryService.get(id));
        model.addAttribute("warehouse", warehouseService.getAll(null));
        return "edit_inventory";
    }

    @DeleteMapping(path = "/delete/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteInventory(@PathVariable(value = "inventoryId") Long id) {
        inventoryService.delete(id);

        return "redirect:/admin/inventories";
    }

    @DeleteMapping(path = "/hide/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String hideInventory(@PathVariable(value = "inventoryId") Long id) {
        inventoryService.softDelete(id);

        return "redirect:/admin/inventories";
    }
}
