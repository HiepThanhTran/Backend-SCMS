package com.fh.scm.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;

    public static List<ErrorResponse> fromBindingResult(BindingResult bindingResult) {
        return bindingResult
                .getAllErrors()
                .stream()
                .map(e -> new ErrorResponse(e.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
