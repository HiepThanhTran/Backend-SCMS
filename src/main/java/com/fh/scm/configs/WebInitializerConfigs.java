package com.fh.scm.configs;

import com.fh.scm.services._InitializerDataService;
import com.fh.scm.services._SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WebInitializerConfigs {

    @Autowired
    private _SystemService systemService;
    @Autowired
    private _InitializerDataService initializerDataService;

    @PostConstruct
    public void initializer() {
        if (isFirstRun()) {
            this.initializerDataService.createUser();
            this.systemService.insert("isFirstRun");
        }
    }

    private boolean isFirstRun() {
        return !this.systemService.isExist("isFirstRun");
    }
}
