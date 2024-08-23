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
        model.addAttribute("tags", this.tagService.findAllWithFilter(params));

        return "tags";
    }

    @GetMapping(path = "/add")
    public String addTag(Model model) {
        model.addAttribute("tag", new Tag());

        return "add_tag";
    }

    @PostMapping(path = "/add")
    public String addTag(Model model, @ModelAttribute(value = "tag") @Valid Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "add_tag";
        }

        this.tagService.save(tag);

        return "redirect:/admin/tags";
    }

    @GetMapping(path = "/edit/{tagId}")
    public String editTag(Model model, @PathVariable(value = "tagId") Long id) {
        model.addAttribute("tag", tagService.findById(id));

        return "edit_tag";
    }

    @PostMapping(path = "/edit/{tagId}")
    public String editTag(Model model, @PathVariable(value = "tagId") Long id,
                          @ModelAttribute(value = "tag") @Valid Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<MessageResponse> errors = MessageResponse.fromBindingResult(bindingResult);
            model.addAttribute("errors", errors);

            return "edit_tag";
        }

        this.tagService.update(tag);

        return "redirect:/admin/tags";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{tagId}")
    public String deleteTag(@PathVariable(value = "tagId") Long id) {
        this.tagService.delete(id);

        return "redirect:/admin/tags";
    }
}
