package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.OrderType;
import com.fh.scms.pojo.Order;
import com.fh.scms.services.InventoryService;
import com.fh.scms.services.OrderService;
import com.fh.scms.services.ProductService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/orders", produces = "application/json; charset=UTF-8")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @ModelAttribute
    public void addAttributes(@NotNull Model model) {
        model.addAttribute("inventories", this.inventoryService.findAllWithFilter(null));
        model.addAttribute("products", this.productService.findAllWithFilter(null));
        model.addAttribute("users", this.userService.findAllWithFilter(null));
    }

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
    public String addOrder(Model model, @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResult,
                           @RequestParam(value = "productIds", required = false) List<Long> productIds,
                           @RequestParam(value = "quantities", required = false) List<Float> quantities,
                           @RequestParam(value = "inventoryId", required = false) Long inventoryId) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResult));

            return "add_order";
        }

        if (validateOrderRequest(model, order, productIds, quantities, inventoryId)) {
            return "add_order";
        }

        try {
            this.orderService.save(order, productIds, quantities, inventoryId);
        } catch (Exception e) {
            model.addAttribute("errors", List.of(new MessageResponse(e.getMessage())));

            return "add_order";
        }

        return "redirect:/admin/orders";
    }

    @GetMapping(path = "/edit/{orderId}")
    public String editOrder(Model model, @PathVariable(value = "orderId") Long id) {
        model.addAttribute("orderDetails", this.orderService.getOrderDetailsById(id));
        model.addAttribute("order", this.orderService.findById(id));

        return "edit_order";
    }

    @PostMapping(path = "/edit/{orderId}")
    public String editOrder(Model model, @PathVariable(value = "orderId") Long id,
                            @ModelAttribute(value = "order") @Valid Order order, BindingResult bindingResultOrder,
                            @RequestParam(value = "productIds", required = false) List<Long> productIds,
                            @RequestParam(value = "quantities", required = false) List<Float> quantities,
                            @RequestParam(value = "inventoryId", required = false) Long inventoryId) {
        model.addAttribute("orderDetails", this.orderService.getOrderDetailsById(id));
        if (bindingResultOrder.hasErrors()) {
            model.addAttribute("errors", MessageResponse.fromBindingResult(bindingResultOrder));

            return "edit_order";
        }

        if (validateOrderRequest(model, order, productIds, quantities, inventoryId)) {
            return "add_order";
        }

        try {
            this.orderService.update(order, productIds, quantities, inventoryId);
        } catch (Exception e) {
            model.addAttribute("errors", List.of(new MessageResponse(e.getMessage())));

            return "edit_order";
        }

        return "redirect:/admin/orders";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{orderId}")
    public void deleteOrder(@PathVariable(value = "orderId") Long id) {
        this.orderService.delete(id);
    }

    private boolean validateOrderRequest(Model model,
                                         @Valid @ModelAttribute("order") Order order,
                                         @RequestParam(value = "productIds", required = false) List<Long> productIds,
                                         @RequestParam(value = "quantities", required = false) List<Float> quantities,
                                         @RequestParam(value = "inventoryId", required = false) Long inventoryId) {
        if (productIds == null || productIds.isEmpty() || quantities == null || quantities.isEmpty() || productIds.size() != quantities.size()) {
            model.addAttribute("errors", List.of(new MessageResponse("Vui lòng chọn sản phẩm cho đơn hàng")));

            return true;
        }

        if (order.getType() == OrderType.INBOUND && inventoryId == null) {
            model.addAttribute("errors", List.of(new MessageResponse("Vui lòng chọn kho hàng")));

            return true;
        }

        return false;
    }
}
