package com.fh.scm.formatters;

import com.fh.scm.pojo.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class CategoryFormatter implements Formatter<Category> {

    @Override
    public @NotNull String print(@NotNull Category category, @NotNull Locale locale) {
        return String.valueOf(category.getId());
    }

    @Override
    public @NotNull Category parse(@NotNull String categoryId, @NotNull Locale locale) throws ParseException {
        Category category = new Category();
        category.setId(Long.parseLong(categoryId));

        return category;
    }
}
