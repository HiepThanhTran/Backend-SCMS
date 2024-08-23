package com.fh.scms.formatters;

import com.fh.scms.pojo.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class TagFormatter implements Formatter<Tag> {

    @Override
    public @NotNull String print(@NotNull Tag tag, @NotNull Locale locale) {
        return String.valueOf(tag.getId());
    }

    @Override
    public @NotNull Tag parse(@NotNull String tagId, @NotNull Locale locale) throws ParseException {
        Tag tag = new Tag();
        tag.setId(Long.parseLong(tagId));

        return tag;
    }
}
