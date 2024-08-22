package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.DeliverySchedule;
import com.fh.scm.services.DeliveryScheduleService;
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
@RequestMapping(path = "/admin/delivery-schedules")
public class DeliveryScheduleController {

    private final DeliveryScheduleService deliveryScheduleService;

    @GetMapping
    public String listDeliverySchedule(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("deliverySchedules", deliveryScheduleService.getAll(params));

        return "delivery_schedules";
    }

    @GetMapping(path = "/{deliveryScheduleId}")
    public String retrieveDeliverySchedule(@PathVariable(value = "deliveryScheduleId") Long id, Model model) {
        model.addAttribute("deliverySchedule", deliveryScheduleService.get(id));

        return "delivery_schedule";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addDeliverySchedule(HttpServletRequest request, Model model, @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule,
                                      BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_delivery_schedule";
            }

            deliveryScheduleService.insert(deliverySchedule);

            return "redirect:/admin/delivery-schedules";
        }

        return "add_delivery_schedule";
    }

    @RequestMapping(path = "/edit/{deliveryScheduleId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editDeliverySchedule(HttpServletRequest request, Model model, @PathVariable(value = "deliveryScheduleId") Long id,
                                       @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_delivery_schedule";
            }

            deliveryScheduleService.update(deliverySchedule);

            return "redirect:/admin/delivery-schedules";
        }

        model.addAttribute("deliverySchedule", deliveryScheduleService.get(id));

        return "edit_delivery_schedule";
    }

    @DeleteMapping(path = "/delete/{deliveryScheduleId}")
    public String deleteDeliverySchedule(@PathVariable(value = "deliveryScheduleId") Long id) {
        deliveryScheduleService.delete(id);

        return "redirect:/admin/delivery-schedules";
    }

    @DeleteMapping(path = "/hide/{deliveryScheduleId}")
    public String hideDeliverySchedule(@PathVariable(value = "deliveryScheduleId") Long id) {
        deliveryScheduleService.softDelete(id);

        return "redirect:/admin/delivery-schedules";
    }
}
