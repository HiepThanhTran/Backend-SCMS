package com.fh.scm.util;

import org.hibernate.query.Query;

import java.util.Map;
import java.util.Optional;

public class Pagination {

    private Pagination() {
    }

    public static void paginator(Query<?> query, Map<String, String> params) {
        if (params != null) {
            String pageStr = Optional.ofNullable(params.get("page"))
                    .filter(page -> !page.isEmpty())
                    .orElse("1");

            int PAGE_SIZE = 20;
            String limitStr = Optional.ofNullable(params.get("limit"))
                    .filter(limit -> !limit.isEmpty())
                    .orElse(String.valueOf(PAGE_SIZE));

            int page, limit;
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
            try {
                limit = Integer.parseInt(limitStr);
                if (limit < 1) {
                    limit = PAGE_SIZE;
                }
            } catch (NumberFormatException e) {
                limit = PAGE_SIZE;
            }

            int start = (page - 1) * PAGE_SIZE;
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
    }
}
