package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.PaymentTermType;
import com.fh.scms.pojo.PaymentTerms;
import com.fh.scms.services.PaymentTermsService;
import com.fh.scms.services.SupplierService;
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
@RequestMapping(path = "/admin/payment-terms", produces = "application/json; charset=UTF-8")
public class PaymentTermsController {

    private final PaymentTermsService paymentTermsService;
    private final SupplierService supplierService;
    
    @GetMapping
    public String listPaymentTerms(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("paymentTerms", this.paymentTermsService.findAllWithFilter(params));

        return "payment_terms";
    }

    @GetMapping(path = "/add")
    public String addPaymentTerms(Model model) {
        model.addAttribute("suppliers", this.supplierService.findAllWithFilter(null));
        model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());
        model.addAttribute("paymentTerms", new PaymentTerms());

        return "add_payment_terms";
    }

    @PostMapping(path = "/add")
    public String addPaymentTerms(Model model, @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("suppliers", this.supplierService.findAllWithFilter(null));
            model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());

            return "add_payment_terms";
        }

        this.paymentTermsService.save(paymentTerms);

        return "redirect:/admin/payment-terms";
    }

    @GetMapping(path = "/edit/{paymentTermsId}")
    public String editPaymentTerms(Model model, @PathVariable(value = "paymentTermsId") Long id) {
        model.addAttribute("suppliers", this.supplierService.findAllWithFilter(null));
        model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());
        model.addAttribute("paymentTerms", this.paymentTermsService.findById(id));

        return "edit_payment_terms";
    }

    @PostMapping(path = "/edit/{paymentTermsId}")
    public String editPaymentTerms(Model model, @PathVariable(value = "paymentTermsId") Long id,
                                   @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);
            model.addAttribute("suppliers", this.supplierService.findAllWithFilter(null));
            model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());

            return "edit_payment_terms";
        }

        this.paymentTermsService.update(paymentTerms);

        return "redirect:/admin/payment-terms";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{paymentTermsId}")
    public String deletePaymentTerms(@PathVariable(value = "paymentTermsId") Long id) {
        this.paymentTermsService.delete(id);

        return "redirect:/admin/payment-terms";
    }
}
