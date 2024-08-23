package com.fh.scms.configs;

import com.fh.scms.services._InitializerDataService;
import com.fh.scms.services._SystemService;
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
            this.initializerDataService.createCategory();
            this.initializerDataService.createTag();
            this.initializerDataService.createUnit();
            this.initializerDataService.createProduct();
            this.initializerDataService.createTax();
            this.initializerDataService.createWarehouse();
            this.initializerDataService.createInventory();
            this.systemService.insert("isFirstRun");
        }
    }

    private boolean isFirstRun() {
        return !this.systemService.isExist("isFirstRun");
    }
}
