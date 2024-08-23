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
@RequestMapping(path = "/admin/schedules", produces = "application/json; charset=UTF-8")
public class DeliveryScheduleController {

    private final DeliveryScheduleService deliveryScheduleService;

    @GetMapping
    public String listDeliverySchedule(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("deliverySchedules", this.deliveryScheduleService.findAllWithFilter(params));

        return "delivery_schedules";
    }

    @GetMapping(path = "/add")
    public String addDeliverySchedule(Model model) {
        model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());
        model.addAttribute("deliverySchedule", new DeliverySchedule());

        return "add_delivery_schedule";
    }

    @PostMapping(path = "/add")
    public String addDeliverySchedule(Model model, @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());

            return "add_delivery_schedule";
        }

        this.deliveryScheduleService.save(deliverySchedule);

        return "redirect:/admin/delivery-schedules";
    }

    @GetMapping(path = "/edit/{deliveryScheduleId}")
    public String editDeliverySchedule(Model model, @PathVariable(value = "deliveryScheduleId") Long id) {
        model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());
        model.addAttribute("deliverySchedule", this.deliveryScheduleService.findById(id));

        return "edit_delivery_schedule";
    }

    @PostMapping(path = "/edit/{deliveryScheduleId}")
    public String editDeliverySchedule(Model model, @PathVariable(value = "deliveryScheduleId") Long id,
                                       @ModelAttribute(value = "deliverySchedule") @Valid DeliverySchedule deliverySchedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());

            return "edit_delivery_schedule";
        }

        this.deliveryScheduleService.update(deliverySchedule);

        return "redirect:/admin/delivery-schedules";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{deliveryScheduleId}")
    public String deleteDeliverySchedule(@PathVariable(value = "deliveryScheduleId") Long id) {
        this.deliveryScheduleService.delete(id);

        return "redirect:/admin/delivery-schedules";
    }
}
