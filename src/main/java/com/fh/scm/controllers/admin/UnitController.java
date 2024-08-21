package com.fh.scm.controllers.admin;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Unit;
import com.fh.scm.services.UnitService;
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
@RequestMapping(path = "/admin/units")
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public String listUnit(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("units", unitService.getAll(params));

        return "units";
    }

    @GetMapping(path = "/{unitId}")
    public String retrieveUnit(@PathVariable(value = "unitId") Long id, Model model) {
        model.addAttribute("unit", unitService.get(id));

        return "unit";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addUnit(HttpServletRequest request, Model model, @ModelAttribute(value = "unit") @Valid Unit unit,
                          BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_unit";
            }

            unitService.insert(unit);

            return "redirect:/admin/units";
        }

        return "add_unit";
    }

    @RequestMapping(path = "/edit/{unitId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editUnit(HttpServletRequest request, Model model, @PathVariable(value = "unitId") Long id,
                           @ModelAttribute(value = "unit") @Valid Unit unit, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_unit";
            }

            unitService.update(unit);

            return "redirect:/admin/units";
        }

        model.addAttribute("unit", unitService.get(id));

        return "edit_unit";
    }

    @DeleteMapping(path = "/delete/{unitId}")
    public String deleteUnit(@PathVariable(value = "unitId") Long id) {
        unitService.delete(id);

        return "redirect:/admin/units";
    }

    @DeleteMapping(path = "/hide/{unitId}")
    public String hideUnit(@PathVariable(value = "unitId") Long id) {
        unitService.softDelete(id);

        return "redirect:/admin/units";
    }
}
