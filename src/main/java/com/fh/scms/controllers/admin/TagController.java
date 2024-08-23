package com.fh.scms.controllers.admin;

import com.fh.scms.dto.MessageResponse;
import com.fh.scms.pojo.Tag;
import com.fh.scms.services.TagService;
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
@RequestMapping(path = "/admin/tags", produces = "application/json; charset=UTF-8")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String listTag(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("tags", this.tagService.getAll(params));

        return "tags";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTag(HttpServletRequest request, Model model, @ModelAttribute(value = "tag") @Valid Tag tag,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_tag";
            }

            this.tagService.insert(tag);

            return "redirect:/admin/tags";
        }

        return "add_tag";
    }

    @RequestMapping(path = "/edit/{tagId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editTag(HttpServletRequest request, Model model, @PathVariable(value = "tagId") Long id,
                          @ModelAttribute(value = "tag") @Valid Tag tag, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_tag";
            }

            this.tagService.update(tag);

            return "redirect:/admin/tags";
        }

        model.addAttribute("tag", tagService.get(id));

        return "edit_tag";
    }

    @DeleteMapping(path = "/delete/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable(value = "tagId") Long id) {
        this.tagService.delete(id);

        return "redirect:/admin/tags";
    }
}
