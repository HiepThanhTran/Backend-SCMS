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
        model.addAttribute("invoices", this.invoiceService.findAllWithFilter(params));

        return "invoices";
    }

    @GetMapping(path = "/add")
    public String addInvoice(Model model) {
        model.addAttribute("invoice", new Invoice());

        return "add_invoice";
    }

    @PostMapping(path = "/add")
    public String addInvoice(Model model, @ModelAttribute(value = "invoice") @Valid Invoice invoice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "add_invoice";
        }

        this.invoiceService.save(invoice);

        return "redirect:/admin/invoices";
    }

    @GetMapping(path = "/edit/{invoiceId}")
    public String editInvoice(Model model, @PathVariable(value = "invoiceId") Long id) {
        model.addAttribute("invoice", this.invoiceService.findById(id));

        return "edit_invoice";
    }

    @PostMapping(path = "/edit/{invoiceId}")
    public String editInvoice(Model model, @PathVariable(value = "invoiceId") Long id,
                              @ModelAttribute(value = "invoice") @Valid Invoice invoice, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "edit_invoice";
        }

        this.invoiceService.update(invoice);

        return "redirect:/admin/invoices";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{invoiceId}")
    public String deleteInvoice(@PathVariable(value = "invoiceId") Long id) {
        this.invoiceService.delete(id);

        return "redirect:/admin/invoices";
    }
}
