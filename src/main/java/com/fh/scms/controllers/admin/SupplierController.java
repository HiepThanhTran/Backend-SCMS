package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.dto.user.UserRequestRegister;
import com.fh.scms.pojo.Supplier;
import com.fh.scms.services.SupplierService;
import com.fh.scms.services.UserService;
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
@RequestMapping(path = "/admin/suppliers", produces = "application/json; charset=UTF-8")
public class SupplierController {

    private final SupplierService supplierService;
    private final UserService userService;

    @GetMapping
    public String listSuppliers(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("suppliers", this.supplierService.getAll(params));

        return "suppliers";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addSupplier(HttpServletRequest request, Model model, @ModelAttribute(value = "supplier") @Valid UserRequestRegister supplier,
                              BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_supplier";
            }

            try {
                this.userService.register(supplier);
            } catch (Exception e) {
                model.addAttribute("errors", List.of(new MessageResponse(e.getMessage())));

                return "add_supplier";
            }

            return "redirect:/admin/suppliers";
        }

        return "add_supplier";
    }

    @RequestMapping(path = "/edit/{supplierId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editSupplier(HttpServletRequest request, Model model, @PathVariable(value = "supplierId") Long id,
                               @ModelAttribute(value = "supplier") @Valid Supplier supplier, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_supplier";
            }

            this.supplierService.update(supplier);

            return "redirect:/admin/suppliers";
        }

        model.addAttribute("supplier", supplierService.get(id));

        return "edit_supplier";
    }

    @DeleteMapping(path = "/delete/{supplierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteSupplier(@PathVariable(value = "supplierId") Long id) {
        this.supplierService.delete(id);

        return "redirect:/admin/suppliers";
    }
}
