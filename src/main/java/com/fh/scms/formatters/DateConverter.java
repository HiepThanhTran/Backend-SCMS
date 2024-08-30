package com.fh.scms.formatters;

import com.fh.scms.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateConverter implements Converter<String, Date> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(@NotNull String source) {
        if (source.isEmpty()) {
            return null;
        }

        return Utils.parseDate(source);
    }
}
