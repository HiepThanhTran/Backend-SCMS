package com.fh.scms.formatters;

import com.fh.scms.pojo.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class UserFormatter implements Formatter<User> {

    @Override
    public @NotNull String print(@NotNull User user, @NotNull Locale locale) {
        return String.valueOf(user.getId());
    }

    @Override
    public @NotNull User parse(@NotNull String userId, @NotNull Locale locale) throws ParseException {
        User user = new User();
        user.setId(Long.parseLong(userId));

        return user;
    }
}
