package com.fh.scms.formatters;

import com.fh.scms.pojo.Unit;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class UnitFormatter implements Formatter<Unit> {

    @Override
    public @NotNull String print(@NotNull Unit unit, @NotNull Locale locale) {
        return String.valueOf(unit.getId());
    }

    @Override
    public @NotNull Unit parse(@NotNull String unitId, @NotNull Locale locale) throws ParseException {
        Unit unit = new Unit();
        unit.setId(Long.parseLong(unitId));

        return unit;
    }
}
