package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Shipment;
import com.fh.scms.services.DeliveryScheduleService;
import com.fh.scms.services.ShipmentService;
import com.fh.scms.services.ShipperService;
import com.fh.scms.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/shipments", produces = "application/json; charset=UTF-8")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipperService shipperService;
    private final WarehouseService warehouseService;
    private final DeliveryScheduleService deliveryScheduleService;

    @ModelAttribute
    public void addAttributes(@NotNull Model model) {
        model.addAttribute("deliverySchedules", this.deliveryScheduleService.findAllWithFilter(null));
        model.addAttribute("warehouses", this.warehouseService.findAllWithFilter(null));
        model.addAttribute("shippers", this.shipperService.findAllWithFilter(null));
    }

    @GetMapping
    public String listShipment(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("shipments", this.shipmentService.findAllWithFilter(params));

        return "shipments";
    }

    @GetMapping(path = "/add")
    public String addShipment(Model model) {
        model.addAttribute("shipment", new Shipment());

        return "add_shipment";
    }

    @PostMapping(path = "/add")
    public String addShipment(Model model, @ModelAttribute(value = "shipment") @Valid Shipment shipment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResult));

            return "add_shipment";
        }

        this.shipmentService.save(shipment);

        return "redirect:/admin/shipments";
    }

    @GetMapping(path = "/edit/{shipmentId}")
    public String editShipment(Model model, @PathVariable(value = "shipmentId") Long id) {
        model.addAttribute("shipment", this.shipmentService.findById(id));

        return "edit_shipment";
    }

    @PostMapping(path = "/edit/{shipmentId}")
    public String editShipment(Model model, @PathVariable(value = "shipmentId") Long id,
                               @ModelAttribute(value = "shipment") @Valid Shipment shipment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResult));

            return "edit_shipment";
        }

        this.shipmentService.update(shipment);

        return "redirect:/admin/shipments";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{shipmentId}")
    public void deleteShipment(@PathVariable(value = "shipmentId") Long id) {
        this.shipmentService.delete(id);
    }
}
