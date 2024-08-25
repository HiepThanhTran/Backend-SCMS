package com.fh.scms.formatters;

import com.fh.scms.pojo.DeliverySchedule;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class ScheduleFormatter implements Formatter<DeliverySchedule> {

    @Override
    public @NotNull String print(@NotNull DeliverySchedule deliverySchedule, @NotNull Locale locale) {
        return String.valueOf(deliverySchedule.getId());
    }

    @Override
    public @NotNull DeliverySchedule parse(@NotNull String deliveryScheduleId, @NotNull Locale locale) throws ParseException {
        DeliverySchedule deliverySchedule = new DeliverySchedule();
        deliverySchedule.setId(Long.parseLong(deliveryScheduleId));

        return deliverySchedule;
    }
}
