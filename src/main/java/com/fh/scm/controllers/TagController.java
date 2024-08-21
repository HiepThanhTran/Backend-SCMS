package com.fh.scm.controllers;

import com.fh.scm.dto.ResponseMessage;
import com.fh.scm.pojo.Tag;
import com.fh.scm.services.TagService;
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
@RequestMapping(path = "/admin/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String listTag(Model model, @RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        model.addAttribute("tags", tagService.getAll(params));

        return "tags";
    }

    @RequestMapping(path = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTag(HttpServletRequest request, Model model, @ModelAttribute(value = "tag") @Valid Tag tag,
                         BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "add_tag";
            }

            tagService.insert(tag);

            return "redirect:/admin/tags";
        }

        return "add_tag";
    }

    @RequestMapping(path = "/edit/{tagId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String editTag(HttpServletRequest request, Model model, @PathVariable(value = "tagId") Long id,
                          @ModelAttribute(value = "tag") @Valid Tag tag, BindingResult bindingResult) {
        if (request.getMethod().equals("POST")) {
            if (bindingResult.hasErrors()) {
                List<ResponseMessage> errors = ResponseMessage.fromBindingResult(bindingResult);
                model.addAttribute("errors", errors);

                return "edit_tag";
            }

            tagService.update(tag);

            return "redirect:/admin/tags";
        }

        model.addAttribute("tag", tagService.get(id));

        return "edit_tag";
    }

    @DeleteMapping(path = "/delete/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable(value = "tagId") Long id) {
        tagService.delete(id);

        return "redirect:/admin/tags";
    }

    @DeleteMapping(path = "/hide/{tagId}")
    public String hideTag(@PathVariable(value = "tagId") Long id) {
        tagService.softDelete(id);

        return "redirect:/admin/tags";
    }
}
