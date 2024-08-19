package com.fh.scm.util;

import com.fh.scm.enums.UserRole;

import java.time.format.DateTimeFormatter;

public final class Constants {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final String HAS_ROLE_ADMIN = String.format("hasRole('ROLE_%s')", UserRole.ADMIN);
    public static final String HAS_ROLE_CUSOMTER = String.format("hasRole('ROLE_%s')", UserRole.CUSTOMER);
    public static final String HAS_ROLE_SUPPLIER = String.format("hasRole('ROLE_%s')", UserRole.SUPPLIER);
    public static final String HAS_ROLE_DISTRIBUTOR = String.format("hasRole('ROLE_%s')", UserRole.DISTRIBUTOR);
    public static final String HAS_ROLE_MANUFACTORER = String.format("hasRole('ROLE_%s')", UserRole.MANUFACTURER);
    public static final String HAS_ROLE_SHIPPER = String.format("hasRole('ROLE_%s')", UserRole.SHIPPER);
}
