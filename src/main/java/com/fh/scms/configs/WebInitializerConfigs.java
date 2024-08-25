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
//        if (!this.globalService.isFirstRun()) {
//            return;
//        }

        this.globalService.createCategory();
        this.globalService.createTag();
        this.globalService.createUnit();
        this.globalService.createTax();
        this.globalService.createWarehouse();
        this.globalService.createUser();
        this.globalService.createProduct();
        this.globalService.createInventory();
        this.globalService.createRating();
//        this.globalService.updateRating();

//        this.globalService.saveFirstRun();
    }
}
