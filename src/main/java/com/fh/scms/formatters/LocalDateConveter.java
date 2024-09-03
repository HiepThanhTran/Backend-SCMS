package com.fh.scms.formatters;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class LocalDateConveter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@NotNull String source) {
        if (source.isEmpty()) {
            return null;
        }

        return LocalDate.parse(source);
    }
}
