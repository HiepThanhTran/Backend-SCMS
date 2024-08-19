package com.fh.scm.controllers;

import com.fh.scm.dto.error.ErrorResponse;
import com.fh.scm.pojo.OrderDetails;
import com.fh.scm.services.OrderDetailsService;
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
@RequestMapping(path = "/admin/order-details")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailService;

    @GetMapping
    public String listOrderDetails(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("orderDetails", orderDetailService.getAll(params));

        return "order_details";
    }

    @GetMapping(path = "/{orderDetailId}")
    public String retrieveOrderDetails(@PathVariable(value = "orderDetailId") Long id, Model model) {
        model.addAttribute("orderDetail", orderDetailService.get(id));

        return "order_detail";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrderDetails(HttpServletRequest request, Model model, @ModelAttribute(value = "orderDetail") @Valid OrderDetails orderDetail,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_order_detail";
            }

            orderDetailService.insert(orderDetail);

            return "redirect:/admin/order-details";
        }

        return "add_order_detail";
    }

    @RequestMapping(path = "/edit/{orderDetailId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editOrderDetails(HttpServletRequest request, Model model, @PathVariable(value = "orderDetailId") Long id,
                          @ModelAttribute(value = "orderDetail") @Valid OrderDetails orderDetail, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ErrorResponse> errors = ErrorResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_order_detail";
            }

            orderDetailService.update(orderDetail);

            return "redirect:/admin/order-details";
        }

        model.addAttribute("orderDetail", orderDetailService.get(id));

        return "edit_order_detail";
    }

    @DeleteMapping(path = "/delete/{orderDetailId}")
    public String deleteOrderDetails(@PathVariable(value = "orderDetailId") Long id) {
        orderDetailService.delete(id);

        return "redirect:/admin/order-details";
    }

    @DeleteMapping(path = "/hide/{orderDetailId}")
    public String hideOrderDetails(@PathVariable(value = "orderDetailId") Long id) {
        orderDetailService.softDelete(id);

        return "redirect:/admin/order-details";
    }
}
