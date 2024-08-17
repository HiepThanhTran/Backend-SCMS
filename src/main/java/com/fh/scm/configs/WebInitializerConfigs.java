package com.fh.scm.configs;

import com.fh.scm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WebInitializerConfigs {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initializer() {
        this.userService.createAdmin();
    }
}
