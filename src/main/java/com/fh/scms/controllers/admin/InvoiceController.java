package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Invoice;
import com.fh.scms.services.InvoiceService;
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
@RequestMapping(path = "/admin/invoices", produces = "application/json; charset=UTF-8")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public String listInvoice(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("invoices", this.invoiceService.getAll(params));

        return "invoices";
    }

    @GetMapping(path = "/{invoiceId}")
    public String retrieveInvoice(@PathVariable(value = "invoiceId") Long id, Model model) {
        model.addAttribute("invoice", this.invoiceService.get(id));

        return "invoice";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addInvoice(HttpServletRequest request, Model model, @ModelAttribute(value = "invoice") @Valid Invoice invoice,
                             BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_invoice";
            }

            this.invoiceService.insert(invoice);

            return "redirect:/admin/invoices";
        }

        return "add_invoice";
    }

    @RequestMapping(path = "/edit/{invoiceId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editInvoice(HttpServletRequest request, Model model, @PathVariable(value = "invoiceId") Long id,
                              @ModelAttribute(value = "invoice") @Valid Invoice invoice, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_invoice";
            }

            this.invoiceService.update(invoice);

            return "redirect:/admin/invoices";
        }

        model.addAttribute("invoice", this.invoiceService.get(id));

        return "edit_invoice";
    }

    @DeleteMapping(path = "/delete/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteInvoice(@PathVariable(value = "invoiceId") Long id) {
        this.invoiceService.delete(id);

        return "redirect:/admin/invoices";
    }
}
