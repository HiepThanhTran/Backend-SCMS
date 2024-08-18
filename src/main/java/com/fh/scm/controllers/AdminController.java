package com.fh.scm.controllers;

import com.fh.scm.services.CustomerService;
import com.fh.scm.services.ShipperService;
import com.fh.scm.services.SupplierService;
import com.fh.scm.services.UserService;
import com.fh.scm.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ShipperService shipperService;

    @ModelAttribute
    public void commonAttributes(Model model) {
        List<String> entities = Utils.generateMappingPojoClass();

        model.addAttribute("entities", entities);
    }

    @GetMapping
    public String dashBoard() {
        return "dashboard";
    }

    @GetMapping(path = "/users")
    public String listUsers(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("users", this.userService.getAll(params));

        return "users";
    }

    @GetMapping(path = "/customers")
    public String listCustomers(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("customers", this.customerService.getAll(params));

        return "customers";
    }

    @GetMapping(path = "/suppliers")
    public String listSupplier(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("suppliers", this.supplierService.getAll(params));

        return "suppliers";
    }

    @GetMapping(path = "/shippers")
    public String listShipper(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("shippers", this.shipperService.getAll(params));

        return "shippers";
    }
}
