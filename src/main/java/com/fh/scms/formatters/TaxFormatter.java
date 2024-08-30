package com.fh.scms.formatters;

import com.fh.scms.pojo.Tax;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class TaxFormatter implements Formatter<Tax> {

    @Override
    public @NotNull String print(@NotNull Tax tax, @NotNull Locale locale) {
        return String.valueOf(tax.getId());
    }

    @Override
    public @NotNull Tax parse(@NotNull String taxId, @NotNull Locale locale) throws ParseException {
        Tax tax = new Tax();
        tax.setId(Long.parseLong(taxId));

        return tax;
    }
}
