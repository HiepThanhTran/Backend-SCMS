package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.DeliveryMethodType;
import com.fh.scms.pojo.DeliverySchedule;
import com.fh.scms.services.DeliveryScheduleService;
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
@RequestMapping(path = "/admin/delivery-schedules", produces = "application/json; charset=UTF-8")
public class DeliveryScheduleController {

    private final DeliveryScheduleService deliveryScheduleService;

    @GetMapping
    public String listDeliverySchedule(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("deliverySchedules", this.deliveryScheduleService.getAll(params));

        return "delivery_schedules";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addDeliverySchedule(HttpServletRequest request, Model model, @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule,
                                      BindingResult bindingResult) {
        model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_delivery_schedule";
            }

            this.deliveryScheduleService.insert(deliverySchedule);

            return "redirect:/admin/delivery-schedules";
        }

        return "add_delivery_schedule";
    }

    @RequestMapping(path = "/edit/{deliveryScheduleId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editDeliverySchedule(HttpServletRequest request, Model model, @PathVariable(value = "deliveryScheduleId") Long id,
                                       @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule, BindingResult bindingResult) {
        model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_delivery_schedule";
            }

            this.deliveryScheduleService.update(deliverySchedule);

            return "redirect:/admin/delivery-schedules";
        }

        model.addAttribute("deliverySchedule", this.deliveryScheduleService.get(id));

        return "edit_delivery_schedule";
    }

    @DeleteMapping(path = "/delete/{deliveryScheduleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteDeliverySchedule(@PathVariable(value = "deliveryScheduleId") Long id) {
        this.deliveryScheduleService.delete(id);

        return "redirect:/admin/delivery-schedules";
    }
}
