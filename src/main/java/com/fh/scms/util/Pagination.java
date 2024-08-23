package com.fh.scms.util;

import org.hibernate.query.Query;

import java.util.Map;

public class Pagination {

    private Pagination() {
    }

    public static void paginator(Query<?> query, Map<String, String> params) {
        if (params != null) {
            String pageStr = params.getOrDefault("page", "ALL");

            if (pageStr.equalsIgnoreCase("ALL")) return;

            String sizeStr = params.getOrDefault("size", String.valueOf(Constants.DEFAULT_PAGE_SIZE));
            int page, size;
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
            try {
                size = Integer.parseInt(sizeStr);
                if (size < 1) size = Constants.DEFAULT_PAGE_SIZE;
            } catch (NumberFormatException e) {
                size = Constants.DEFAULT_PAGE_SIZE;
            }

            int start = (page - 1) * size;
            query.setFirstResult(start);
            query.setMaxResults(size);
        }
    }
}
