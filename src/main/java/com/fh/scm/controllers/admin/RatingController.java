package com.fh.scm.controllers.admin;

import com.fh.scm.dto.MessageResponse;
import com.fh.scm.pojo.Rating;
import com.fh.scm.services.RatingService;
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
@RequestMapping(path = "/admin/ratings", produces = "application/json; charset=UTF-8")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public String listRating(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("ratings", ratingService.getAll(params));

        return "ratings";
    }

    @GetMapping(path = "/{ratingId}")
    public String retrieveRating(@PathVariable(value = "ratingId") Long id, Model model) {
        model.addAttribute("rating", ratingService.get(id));

        return "rating";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addRating(HttpServletRequest request, Model model, @ModelAttribute(value = "rating") @Valid Rating rating,
                            BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_rating";
            }

            ratingService.insert(rating);

            return "redirect:/admin/ratings";
        }

        return "add_rating";
    }

    @RequestMapping(path = "/edit/{ratingId}", method = {RequestMethod.GET, RequestMethod.PATCH})
    public String editRating(HttpServletRequest request, Model model, @PathVariable(value = "ratingId") Long id,
                             @ModelAttribute(value = "rating") @Valid Rating rating, BindingResult bindingResult) {
        if (request.getMethod().equals("PATCH")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_rating";
            }

            ratingService.update(rating);

            return "redirect:/admin/ratings";
        }

        model.addAttribute("rating", ratingService.get(id));

        return "edit_rating";
    }

    @DeleteMapping(path = "/delete/{ratingId}")
    public String deleteRating(@PathVariable(value = "ratingId") Long id) {
        ratingService.delete(id);

        return "redirect:/admin/ratings";
    }

    @DeleteMapping(path = "/hide/{ratingId}")
    public String hideRating(@PathVariable(value = "ratingId") Long id) {
        ratingService.softDelete(id);

        return "redirect:/admin/ratings";
    }
}
