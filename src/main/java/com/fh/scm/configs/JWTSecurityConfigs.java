package com.fh.scm.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(1)
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.fh.scm.components",
        "com.fh.scm.controllers",
        "com.fh.scm.repository",
        "com.fh.scm.services"})
public class JWTSecurityConfigs extends WebSecurityConfigurerAdapter {
}
