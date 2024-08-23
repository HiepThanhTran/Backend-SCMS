package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Order;
import com.fh.scms.services.OrderService;
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
@RequestMapping(path = "/admin/orders", produces = "application/json; charset=UTF-8")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String listOrder(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("orders", this.orderService.getAll(params));

        return "orders";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrder(HttpServletRequest request, Model model, @ModelAttribute(value = "order") @Valid Order order,
                           BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_order";
            }

            this.orderService.insert(order);

            return "redirect:/admin/orders";
        }

        return "add_order";
    }

    @RequestMapping(path = "/edit/{orderId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editOrder(HttpServletRequest request, Model model, @PathVariable(value = "orderId") Long id,
                            @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_order";
            }

            this.orderService.update(order);

            return "redirect:/admin/orders";
        }

        return "edit_order";
    }

    @DeleteMapping(path = "/delete/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteOrder(@PathVariable(value = "orderId") Long id) {
        this.orderService.delete(id);

        return "redirect:/admin/orders";
    }
}
