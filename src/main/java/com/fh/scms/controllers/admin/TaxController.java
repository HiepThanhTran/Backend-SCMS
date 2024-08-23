package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Tax;
import com.fh.scms.services.TaxService;
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
@RequestMapping(path = "/admin/taxs", produces = "application/json; charset=UTF-8")
public class TaxController {

    private final TaxService taxService;

    @GetMapping
    public String listTax(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("taxs", this.taxService.getAll(params));

        return "taxs";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTax(HttpServletRequest request, Model model, @ModelAttribute(value = "tax") @Valid Tax tax,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_tax";
            }

            this.taxService.insert(tax);

            return "redirect:/admin/taxs";
        }

        return "add_tax";
    }

    @RequestMapping(path = "/edit/{taxId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editTax(HttpServletRequest request, Model model, @PathVariable(value = "taxId") Long id,
                          @ModelAttribute(value = "tax") @Valid Tax tax, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_tax";
            }

            this.taxService.update(tax);

            return "redirect:/admin/taxs";
        }

        model.addAttribute("tax", this.taxService.get(id));

        return "edit_tax";
    }

    @DeleteMapping(path = "/delete/{taxId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTax(@PathVariable(value = "taxId") Long id) {
        this.taxService.delete(id);

        return "redirect:/admin/taxs";
    }
}
