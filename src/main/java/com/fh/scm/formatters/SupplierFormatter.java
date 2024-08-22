package com.fh.scm.formatters;

import com.fh.scm.pojo.Supplier;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class SupplierFormatter implements Formatter<Supplier> {

    @Override
    public @NotNull String print(@NotNull Supplier supplier, @NotNull Locale locale) {
        return String.valueOf(supplier.getId());
    }

    @Override
    public @NotNull Supplier parse(@NotNull String supplierId, @NotNull Locale locale) throws ParseException {
        Supplier supplier = new Supplier();
        supplier.setId(Long.parseLong(supplierId));

        return supplier;
    }
}
