package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Tax;
import com.fh.scm.services.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/admin/taxs")
public class TaxController {

    private final TaxService taxService;

    @GetMapping
    public String listTax(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("taxs", taxService.getAll(params));

        return "taxs";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTax(HttpServletRequest request, Model model, @ModelAttribute(value = "tax") @Valid Tax tax,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_tax";
            }

            taxService.insert(tax);

            return "redirect:/admin/taxs";
        }

        return "add_tax";
    }

    @RequestMapping(path = "/edit/{taxId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editTax(HttpServletRequest request, Model model, @PathVariable(value = "taxId") Long id,
                          @ModelAttribute(value = "tax") @Valid Tax tax, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_tax";
            }

            taxService.update(tax);

            return "redirect:/admin/taxs";
        }

        model.addAttribute("tax", taxService.get(id));

        return "edit_tax";
    }

    @DeleteMapping(path = "/delete/{taxId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTax(@PathVariable(value = "taxId") Long id) {
        taxService.delete(id);

        return "redirect:/admin/taxs";
    }

    @DeleteMapping(path = "/hide/{taxId}")
    public String hideTax(@PathVariable(value = "taxId") Long id) {
        taxService.softDelete(id);

        return "redirect:/admin/taxs";
    }
}
