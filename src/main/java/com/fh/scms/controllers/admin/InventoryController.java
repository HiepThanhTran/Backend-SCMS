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
        model.addAttribute("inventories", this.inventoryService.findAllWithFilter(params));

        return "inventories";
    }

    @GetMapping(path = "/add")
    public String addInventory(Model model) {
        model.addAttribute("warehouses", this.warehouseService.findAllWithFilter(null));
        model.addAttribute("inventory", new Inventory());

        return "add_inventory";
    }

    @PostMapping(path = "/add")
    public String addInventory(Model model, @ModelAttribute(value = "inventory") @Valid Inventory inventory,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("warehouses", this.warehouseService.findAllWithFilter(null));

            return "add_inventory";
        }

        this.inventoryService.save(inventory);

        return "redirect:/admin/inventories";
    }

    @GetMapping(path = "/edit/{inventoryId}")
    public String editInventory(Model model, @PathVariable(value = "inventoryId") Long id) {
        model.addAttribute("warehouses", this.warehouseService.findAllWithFilter(null));
        model.addAttribute("inventory", this.inventoryService.findById(id));

        return "edit_inventory";
    }

    @PostMapping(path = "/edit/{inventoryId}")
    public String editInventory(Model model, @PathVariable(value = "inventoryId") Long id,
                                @ModelAttribute(value = "inventory") @Valid Inventory inventory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("warehouse", warehouseService.findAllWithFilter(null));

            return "edit_inventory";
        }

        this.inventoryService.update(inventory);

        return "redirect:/admin/inventories";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{inventoryId}")
    public String deleteInventory(@PathVariable(value = "inventoryId") Long id) {
        this.inventoryService.delete(id);

        return "redirect:/admin/inventories";
    }
}
