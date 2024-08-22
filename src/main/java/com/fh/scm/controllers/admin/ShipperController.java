package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.Shipper;
import com.fh.scm.services.ShipperService;
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
@RequestMapping(path = "/admin/shippers")
public class ShipperController {

    private final ShipperService shipperService;

    @GetMapping
    public String listShippers(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("shippers", shipperService.getAll(params));

        return "shippers";
    }

    @GetMapping(path = "/{shipperId}")
    public String retrieveShipper(@PathVariable(value = "shipperId") Long id, Model model) {
        model.addAttribute("shipper", shipperService.get(id));

        return "shipper";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addShipper(HttpServletRequest request, Model model, @ModelAttribute(value = "shipper") @Valid Shipper shipper,
                             BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_shipper";
            }

            shipperService.insert(shipper);

            return "redirect:/admin/shippers";
        }

        return "add_shipper";
    }

    @RequestMapping(path = "/edit/{shipperId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editShipper(HttpServletRequest request, Model model, @PathVariable(value = "shipperId") Long id,
                              @ModelAttribute(value = "shipper") @Valid Shipper shipper, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_shipper";
            }

            shipperService.update(shipper);

            return "redirect:/admin/shippers";
        }

        return "edit_shipper";
    }

    @DeleteMapping(path = "/delete/{shipperId}")
    public String deleteShipper(@PathVariable(value = "shipperId") Long id) {
        shipperService.delete(id);

        return "redirect:/admin/shippers";
    }

    @DeleteMapping(path = "/hide/{shipperId}")
    public String hideShipper(@PathVariable(value = "shipperId") Long id) {
        shipperService.softDelete(id);

        return "redirect:/admin/shippers";
    }
}
