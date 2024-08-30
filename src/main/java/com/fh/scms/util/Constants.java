package com.fh.scms.util;

import java.text.SimpleDateFormat;

public final class Constants {

    public static final SimpleDateFormat DATE_TIME_FORMAT_FRIENDLY = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int EXPIRING_SOON_DAYS = 30;
    public static final int RECENTLY_ORDERS_NUMBER = 10;
    public static final int CURRENT_WEEK = 0;
    public static final int LAST_WEEK = 7;
}
