package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Shipment;
import com.fh.scms.services.DeliveryScheduleService;
import com.fh.scms.services.ShipmentService;
import com.fh.scms.services.ShipperService;
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
@RequestMapping(path = "/admin/shipments", produces = "application/json; charset=UTF-8")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final ShipperService shipperService;
    private final WarehouseService warehouseService;
    private final DeliveryScheduleService deliveryScheduleService;

    @GetMapping
    public String listShipment(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("shipments", this.shipmentService.getAll(params));

        return "shipments";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addShipment(HttpServletRequest request, Model model, @ModelAttribute(value = "shipment") @Valid Shipment shipment,
                              BindingResult bindingResult) {
        model.addAttribute("shippers", this.shipperService.getAll(null));
        model.addAttribute("warehouses", this.warehouseService.getAll(null));
        model.addAttribute("deliverySchedules", this.deliveryScheduleService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_shipment";
            }

            this.shipmentService.insert(shipment);

            return "redirect:/admin/shipments";
        }

        return "add_shipment";
    }

    @RequestMapping(path = "/edit/{shipmentId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editShipment(HttpServletRequest request, Model model, @PathVariable(value = "shipmentId") Long id,
                               @ModelAttribute(value = "shipment") @Valid Shipment shipment, BindingResult bindingResult) {
        model.addAttribute("shippers", this.shipperService.getAll(null));
        model.addAttribute("warehouses", this.warehouseService.getAll(null));
        model.addAttribute("deliverySchedules", this.deliveryScheduleService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_shipment";
            }

            this.shipmentService.update(shipment);

            return "redirect:/admin/shipments";
        }

        model.addAttribute("shipment", this.shipmentService.get(id));

        return "edit_shipment";
    }

    @DeleteMapping(path = "/delete/{shipmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteShipment(@PathVariable(value = "shipmentId") Long id) {
        this.shipmentService.delete(id);

        return "redirect:/admin/shipments";
    }
}
