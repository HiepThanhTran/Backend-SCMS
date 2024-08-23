package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Inventory;
import com.fh.scms.services.InventoryService;
import com.fh.scms.services.WarehouseService;
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
        model.addAttribute("inventories", this.inventoryService.getAll(params));

        return "inventories";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addInventory(HttpServletRequest request, Model model, @ModelAttribute(value = "inventory") @Valid Inventory inventory,
                               BindingResult bindingResult) {
        model.addAttribute("warehouses", this.warehouseService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_inventory";
            }

            this.inventoryService.insert(inventory);
            return "redirect:/admin/inventories";
        }

        return "add_inventory";
    }

    @RequestMapping(path = "/edit/{inventoryId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editInventory(HttpServletRequest request, Model model, @PathVariable(value = "inventoryId") Long id,
                                @ModelAttribute(value = "inventory") @Valid Inventory inventory, BindingResult bindingResult) {
        model.addAttribute("warehouse", warehouseService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_inventory";
            }

            this.inventoryService.update(inventory);

            return "redirect:/admin/inventories";
        }

        model.addAttribute("inventory", inventoryService.get(id));

        return "edit_inventory";
    }

    @DeleteMapping(path = "/delete/{inventoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteInventory(@PathVariable(value = "inventoryId") Long id) {
        this.inventoryService.delete(id);

        return "redirect:/admin/inventories";
    }
}
