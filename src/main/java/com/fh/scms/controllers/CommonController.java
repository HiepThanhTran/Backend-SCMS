package com.fh.scms.controllers;

import com.fh.scms.enums.*;
import com.fh.scms.util.Utils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class CommonController {

    @ModelAttribute
    public void commonAttributes(@NotNull Model model) {
        model.addAttribute("entities", Utils.generateMappingPojoClass());
        model.addAttribute("addressTypes", AddressType.getAllDisplayNames());
        model.addAttribute("criteriaTypes", CriteriaType.getAllDisplayNames());
        model.addAttribute("deliveryMethods", DeliveryMethodType.getAllDisplayNames());
        model.addAttribute("orderStatus", OrderStatus.getAllDisplayNames());
        model.addAttribute("orderTypes", OrderType.getAllDisplayNames());
        model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());
        model.addAttribute("productStatus", ProductStatus.getAllDisplayNames());
        model.addAttribute("shipmentStatus", ShipmentStatus.getAllDisplayNames());
        model.addAttribute("userRoles", UserRole.getAllDisplayNames());
    }
}
