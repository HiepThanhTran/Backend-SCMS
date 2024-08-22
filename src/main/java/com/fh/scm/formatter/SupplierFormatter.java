/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fh.scm.formatter;

import com.fh.scm.pojo.Supplier;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author songh
 */
public class SupplierFormatter implements Formatter<Supplier> {

    @Override
    public String print(Supplier t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Supplier parse(String supplierId, Locale locale) throws ParseException {
        Supplier supplier = new Supplier();
        supplier.setId(Long.parseLong(supplierId));
        return supplier;
    }
}
