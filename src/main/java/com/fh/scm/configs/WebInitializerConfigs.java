package com.fh.scm.configs;

import com.fh.scm.services.InitializerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class WebInitializerConfigs {

    private final InitializerService initializerService;

    @PostConstruct
    public void initializer() {
        this.initializerService.createAdmin();
    }
}
