package com.fh.scm.configs;

import com.fh.scm.enums.UserRole;
import com.fh.scm.filters.CustomAccessDeniedHandler;
import com.fh.scm.filters.JWTAuthenticationTokenFilter;
import com.fh.scm.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    @Bean
    public JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JWTAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(this.authenticationManager());

        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/**");

        http.authorizeRequests()
                // User
                .antMatchers("/api/user/profile/**").authenticated()
                .antMatchers("/api/user/confirm").authenticated()
                .antMatchers("/api/user/**").permitAll()
                // Supplier
                .antMatchers("/api/supplier/payment-terms/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .antMatchers("/api/supplier/profile/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .antMatchers("/api/supplier/**/rating/add").authenticated()
                .antMatchers("/api/supplier/**").permitAll()
                // Rating
                .antMatchers("/api/rating/**/edit").authenticated()
                .antMatchers("/api/rating/**/delete").authenticated()
                .antMatchers("/api/rating/**").permitAll()
                .and()
                .antMatcher("/api/**").httpBasic().authenticationEntryPoint(this.restServicesEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                .and()
                .addFilterBefore(this.jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler());
    }
}
