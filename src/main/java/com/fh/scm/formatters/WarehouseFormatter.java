package com.fh.scm.formatters;

import com.fh.scm.pojo.Warehouse;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class WarehouseFormatter implements Formatter<Warehouse> {

    @Override
    public @NotNull String print(@NotNull Warehouse warehouse, @NotNull Locale locale) {
        return String.valueOf(warehouse.getId());
    }

    @Override
    public @NotNull Warehouse parse(@NotNull String warehouseId, @NotNull Locale locale) throws ParseException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(Long.parseLong(warehouseId));

        return warehouse;
    }
}
