package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Order;
import com.fh.scm.services.OrderService;
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
@RequestMapping(path = "/admin/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String listOrder(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("orders", orderService.getAll(params));

        return "orders";
    }

    @GetMapping(path = "/{orderId}")
    public String retrieveOrder(@RequestParam(value = "orderId") Long id, Model model) {
        model.addAttribute("order", orderService.get(id));

        return "order";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrder(HttpServletRequest request, Model model, @ModelAttribute(value = "order") @Valid Order order,
                           BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_order";
            }

            orderService.insert(order);

            return "redirect:/admin/orders";
        }

        return "add_order";
    }

    @RequestMapping(path = "/edit/{orderId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editOrder(HttpServletRequest request, Model model, @PathVariable(value = "orderId") Long id,
                            @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_order";
            }

            orderService.update(order);

            return "redirect:/admin/orders";
        }

        return "edit_order";
    }

    @DeleteMapping(path = "/delete/{orderId}")
    public String deleteOrder(@PathVariable(value = "orderId") Long id) {
        orderService.delete(id);

        return "redirect:/admin/orders";
    }

    @DeleteMapping(path = "/hide/{orderId}")
    public String hideOrder(@PathVariable(value = "orderId") Long id) {
        orderService.softDelete(id);

        return "redirect:/admin/orders";
    }
}
