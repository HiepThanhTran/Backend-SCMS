package com.fh.scms.controllers;

import com.fh.scms.util.Utils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class CommonController {

    @ModelAttribute
    public void commonAttributes(@NotNull Model model) {
        List<Map.Entry<String, String>> entities = Utils.generateMappingPojoClass();
        model.addAttribute("entities", entities);
    }
}
