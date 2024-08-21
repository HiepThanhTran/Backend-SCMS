/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fh.scm.formatter;

import com.fh.scm.pojo.Warehouse;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author songh
 */
public class WarehouseFormatter implements Formatter<Warehouse> {

    @Override
    public String print(Warehouse t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public Warehouse parse(String warehouseId, Locale locale) throws ParseException {
        Warehouse wareHouse = new Warehouse();
        wareHouse.setId(Long.parseLong(warehouseId));
        return wareHouse;
    }
}
