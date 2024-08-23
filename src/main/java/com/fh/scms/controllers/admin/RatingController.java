package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.enums.CriteriaType;
import com.fh.scms.pojo.Rating;
import com.fh.scms.services.RatingService;
import com.fh.scms.services.SupplierService;
import com.fh.scms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Transactional
@RequestMapping(path = "/admin/ratings", produces = "application/json; charset=UTF-8")
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;
    private final SupplierService supplierService;

    @GetMapping
    public String listRating(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("ratings", this.ratingService.getAll(params));

        return "ratings";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addRating(HttpServletRequest request, Model model, @ModelAttribute(value = "rating") @Valid Rating rating,
                            BindingResult bindingResult) {
        model.addAttribute("criterias", CriteriaType.getAllDisplayNames());
        model.addAttribute("suppliers", this.supplierService.getAll(null));
        model.addAttribute("users", this.userService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_rating";
            }

            this.ratingService.insert(rating);

            return "redirect:/admin/ratings";
        }

        return "add_rating";
    }

    @RequestMapping(path = "/edit/{ratingId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editRating(HttpServletRequest request, Model model, @PathVariable(value = "ratingId") Long id,
                             @ModelAttribute(value = "rating") @Valid Rating rating, BindingResult bindingResult) {
        model.addAttribute("criterias", CriteriaType.getAllDisplayNames());
        model.addAttribute("suppliers", this.supplierService.getAll(null));
        model.addAttribute("users", this.userService.getAll(null));

        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_rating";
            }

            this.ratingService.update(rating);

            return "redirect:/admin/ratings";
        }

        model.addAttribute("rating", this.ratingService.get(id));

        return "edit_rating";
    }

    @DeleteMapping(path = "/delete/{ratingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteRating(@PathVariable(value = "ratingId") Long id) {
        this.ratingService.delete(id);

        return "redirect:/admin/ratings";
    }
}
