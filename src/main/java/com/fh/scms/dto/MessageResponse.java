package com.fh.scms.dto;

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
public class MessageResponse {

    private String message;

    public static List<MessageResponse> fromBindingResult(@NotNull BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(e -> new MessageResponse(e.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
