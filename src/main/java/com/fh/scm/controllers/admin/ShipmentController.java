package com.fh.scm.controllers.admin;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Shipment;
import com.fh.scm.services.ShipmentService;
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
@RequestMapping(path = "/admin/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    public String listShipment(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("shipments", shipmentService.getAll(params));

        return "shipments";
    }

    @GetMapping(path = "/{shipmentId}")
    public String retrieveShipment(@PathVariable(value = "shipmentId") Long id, Model model) {
        model.addAttribute("shipment", shipmentService.get(id));

        return "shipment";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addShipment(HttpServletRequest request, Model model, @ModelAttribute(value = "shipment") @Valid Shipment shipment,
                              BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_shipment";
            }

            shipmentService.insert(shipment);

            return "redirect:/admin/shipments";
        }

        return "add_shipment";
    }

    @RequestMapping(path = "/edit/{shipmentId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editShipment(HttpServletRequest request, Model model, @PathVariable(value = "shipmentId") Long id,
                               @ModelAttribute(value = "shipment") @Valid Shipment shipment, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_shipment";
            }

            shipmentService.update(shipment);

            return "redirect:/admin/shipments";
        }

        model.addAttribute("shipment", shipmentService.get(id));

        return "edit_shipment";
    }

    @DeleteMapping(path = "/delete/{shipmentId}")
    public String deleteShipment(@PathVariable(value = "shipmentId") Long id) {
        shipmentService.delete(id);

        return "redirect:/admin/shipments";
    }

    @DeleteMapping(path = "/hide/{shipmentId}")
    public String hideShipment(@PathVariable(value = "shipmentId") Long id) {
        shipmentService.softDelete(id);

        return "redirect:/admin/shipments";
    }
}
