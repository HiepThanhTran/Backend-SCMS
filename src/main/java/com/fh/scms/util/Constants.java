package com.fh.scms.util;

import java.time.format.DateTimeFormatter;

public final class Constants {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final int DEFAULT_PAGE_SIZE = 20;
}
