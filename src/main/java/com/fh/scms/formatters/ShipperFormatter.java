package com.fh.scms.formatters;

import com.fh.scms.pojo.Shipper;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ShipperFormatter implements Formatter<Shipper> {

    @Override
    public @NotNull String print(@NotNull Shipper shipper, @NotNull Locale locale) {
        return String.valueOf(shipper.getId());
    }

    @Override
    public @NotNull Shipper parse(@NotNull String shipperId, @NotNull Locale locale) throws ParseException {
        Shipper shipper = new Shipper();
        shipper.setId(Long.parseLong(shipperId));

        return shipper;
    }
}
