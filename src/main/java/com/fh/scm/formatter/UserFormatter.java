/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fh.scm.formatter;

import com.fh.scm.pojo.Supplier;
import com.fh.scm.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author songh
 */
public class UserFormatter implements Formatter<User> {

    @Override
    public String print(User t, Locale locale) {
        return String.valueOf(t.getId());
    }

    @Override
    public User parse(String userId, Locale locale) throws ParseException {
        User user = new User();
        user.setId(Long.parseLong(userId));
        return user;
    }
}
