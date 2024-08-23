package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Warehouse;
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
@RequestMapping(path = "/admin/warehouses", produces = "application/json; charset=UTF-8")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public String listWarehouse(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("warehouses", this.warehouseService.findAllWithFilter(params));

        return "warehouses";
    }

    @GetMapping(path = "/add")
    public String addWarehouse(Model model) {
        model.addAttribute("warehouse", new Warehouse());

        return "add_warehouse";
    }

    @PostMapping(path = "/add")
    public String addWarehouse(Model model, @ModelAttribute(value = "warehouse") @Valid Warehouse warehouse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "add_warehouse";
        }

        this.warehouseService.save(warehouse);

        return "redirect:/admin/warehouses";
    }

    @GetMapping (path = "/edit/{warehouseId}")
    public String editWarehouse(Model model, @PathVariable(value = "warehouseId") Long id) {
        model.addAttribute("warehouse", this.warehouseService.findById(id));

        return "edit_warehouse";
    }

    @PostMapping(path = "/edit/{warehouseId}")
    public String editWarehouse(Model model, @PathVariable(value = "warehouseId") Long id,
                                @ModelAttribute(value = "warehouse") @Valid Warehouse warehouse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "edit_warehouse";
        }

        this.warehouseService.update(warehouse);

        return "redirect:/admin/warehouses";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{warehouseId}")
    public String deleteWarehouse(@PathVariable(value = "warehouseId") Long id) {
        this.warehouseService.delete(id);

        return "redirect:/admin/warehouses";
    }
}
