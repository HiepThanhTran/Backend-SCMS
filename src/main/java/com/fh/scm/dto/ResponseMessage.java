package com.fh.scm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private String message;

    public static List<ResponseMessage> fromBindingResult(@NotNull BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(e -> new ResponseMessage(e.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
