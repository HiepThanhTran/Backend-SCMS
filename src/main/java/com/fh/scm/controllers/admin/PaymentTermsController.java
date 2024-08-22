package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.PaymentTerms;
import com.fh.scm.services.PaymentTermsService;
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
@RequestMapping(path = "/admin/payment-terms", produces = "application/json; charset=UTF-8")
public class PaymentTermsController {

    private final PaymentTermsService paymentTermsService;

    @GetMapping
    public String listPaymentTerms(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("paymentTerms", paymentTermsService.getAll(params));

        return "payment_terms";
    }

    @GetMapping(path = "/{paymentTermsId}")
    public String retrievePaymentTerms(@PathVariable(value = "paymentTermsId") Long id, Model model) {
        model.addAttribute("paymentTerms", paymentTermsService.get(id));

        return "payment_term";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addPaymentTerms(HttpServletRequest request, Model model, @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms,
                                  BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_payment_terms";
            }

            paymentTermsService.insert(paymentTerms);

            return "redirect:/admin/payment-terms";
        }

        return "add_payment_terms";
    }

    @RequestMapping(path = "/edit/{paymentTermsId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editPaymentTerms(HttpServletRequest request, Model model, @PathVariable(value = "paymentTermsId") Long id,
                                   @ModelAttribute(value = "paymentTerms") @Valid PaymentTerms paymentTerms, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_payment_terms";
            }

            paymentTermsService.update(paymentTerms);

            return "redirect:/admin/payment-terms";
        }

        model.addAttribute("paymentTerms", paymentTermsService.get(id));

        return "edit_payment_terms";
    }

    @DeleteMapping(path = "/delete/{paymentTermsId}")
    public String deletePaymentTerms(@PathVariable(value = "paymentTermsId") Long id) {
        paymentTermsService.delete(id);

        return "redirect:/admin/payment-terms";
    }

    @DeleteMapping(path = "/hide/{paymentTermsId}")
    public String hidePaymentTerms(@PathVariable(value = "paymentTermsId") Long id) {
        paymentTermsService.softDelete(id);

        return "redirect:/admin/payment-terms";
    }
}
