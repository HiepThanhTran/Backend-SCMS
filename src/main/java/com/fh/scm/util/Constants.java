package com.fh.scm.util;

import com.fh.scm.enums.Role;

public final class Constants {

    public static final String HAS_ROLE_ADMIN = String.format("hasRole('ROLE_%s')", Role.ADMIN);
    public static final String HAS_ROLE_SUPPLIER = String.format("hasRole('ROLE_%s')", Role.SUPPLIER);
    public static final String HAS_ROLE_SHIPPER = String.format("hasRole('ROLE_%s')", Role.SHIPPER);
    public static final String HAS_ROLE_CUSOMTER = String.format("hasRole('ROLE_%s')", Role.CUSTOMER);
    public static final String HAS_ROLE_DISTRIBUTOR = String.format("hasRole('ROLE_%s')", Role.DISTRIBUTOR);
    public static final String HAS_ROLE_MANUFACTORER = String.format("hasRole('ROLE_%s')", Role.MANUFACTURER);
}
