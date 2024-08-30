package com.fh.scms.formatters;

import com.fh.scms.pojo.Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class OrderFormatter implements Formatter<Order> {

    @Override
    public @NotNull String print(@NotNull Order order, @NotNull Locale locale) {
        return String.valueOf(order.getId());
    }

    @Override
    public @NotNull Order parse(@NotNull String orderId, @NotNull Locale locale) throws ParseException {
        Order order = new Order();
        order.setId(Long.parseLong(orderId));

        return order;
    }
}
