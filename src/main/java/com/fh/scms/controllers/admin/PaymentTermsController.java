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
        model.addAttribute("paymentTerms", this.paymentTermsService.getAll(params));

        return "payment_terms";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addPaymentTerms(HttpServletRequest request, Model model, @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms,
                                  BindingResult bindingResult) {
        model.addAttribute("suppliers", this.supplierService.getAll(null));
        model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_payment_terms";
            }

            this.paymentTermsService.insert(paymentTerms);

            return "redirect:/admin/payment-terms";
        }

        return "add_payment_terms";
    }

    @RequestMapping(path = "/edit/{paymentTermsId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editPaymentTerms(HttpServletRequest request, Model model, @PathVariable(value = "paymentTermsId") Long id,
                                   @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms, BindingResult bindingResult) {
        model.addAttribute("suppliers", this.supplierService.getAll(null));
        model.addAttribute("paymentTermTypes", PaymentTermType.getAllDisplayNames());

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_payment_terms";
            }

            this.paymentTermsService.update(paymentTerms);

            return "redirect:/admin/payment-terms";
        }

        model.addAttribute("paymentTerms", this.paymentTermsService.get(id));

        return "edit_payment_terms";
    }

    @DeleteMapping(path = "/delete/{paymentTermsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deletePaymentTerms(@PathVariable(value = "paymentTermsId") Long id) {
        this.paymentTermsService.delete(id);

        return "redirect:/admin/payment-terms";
    }
}
