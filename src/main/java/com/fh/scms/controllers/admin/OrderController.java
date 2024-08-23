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
        model.addAttribute("orders", this.orderService.findAllWithFilter(params));

        return "orders";
    }

    @GetMapping(path = "/add")
    public String addOrder(Model model) {
        model.addAttribute("order", new Order());

        return "add_order";
    }

    @PostMapping(path = "/add")
    public String addOrder(Model model, @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "add_order";
        }

        this.orderService.save(order);

        return "redirect:/admin/orders";
    }

    @GetMapping(path = "/edit/{orderId}")
    public String editOrder(Model model, @PathVariable(value = "orderId") Long id) {
        model.addAttribute("order", this.orderService.findById(id));

        return "edit_order";
    }

    @PostMapping(path = "/edit/{orderId}")
    public String editOrder(Model model, @PathVariable(value = "orderId") Long id,
                            @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "edit_order";
        }

        this.orderService.update(order);

        return "redirect:/admin/orders";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{orderId}")
    public String deleteOrder(@PathVariable(value = "orderId") Long id) {
        this.orderService.delete(id);

        return "redirect:/admin/orders";
    }
}
