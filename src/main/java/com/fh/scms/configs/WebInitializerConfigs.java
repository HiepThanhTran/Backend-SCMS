package com.fh.scms.configs;

import com.fh.scms.components.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WebInitializerConfigs {

    @Autowired
    private GlobalService globalService;

    @PostConstruct
    public void initializer() {
        if (!this.globalService.isFirstRun()) {
            this.globalService.createUser();
            this.globalService.createCategory();
            this.globalService.createTag();
            this.globalService.createUnit();
            this.globalService.createProduct();
            this.globalService.createTax();
            this.globalService.createWarehouse();
            this.globalService.createInventory();
            this.globalService.saveFirstRun();
        }
    }
}
