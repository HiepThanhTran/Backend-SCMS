package com.fh.scms.configs;

import com.fh.scms.enums.UserRole;
import com.fh.scms.filters.CustomAccessDeniedHandler;
import com.fh.scms.filters.JWTAuthenticationTokenFilter;
import com.fh.scms.filters.RestAuthenticationEntryPoint;
import org.jetbrains.annotations.NotNull;
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Order(1)
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.fh.scms.components",
        "com.fh.scms.controllers",
        "com.fh.scms.repository",
        "com.fh.scms.services"})
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
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedHeaders(List.of("*"));
            cors.setAllowCredentials(true);
            return cors;
        });

        http.csrf().ignoringAntMatchers("/api/**");

        http.authorizeRequests()
                // Cart
                .antMatchers("/api/cart/**").authenticated()
                // Category
                .antMatchers("/api/categories/**").permitAll()
                // Customer
                .antMatchers("/api/customers/profile/**").hasRole(UserRole.ROLE_CUSTOMER.alias())
                .antMatchers("/api/customers/**").permitAll()
                // Invoice
                .antMatchers("/api/invoices/**").authenticated()
                // Order
                .antMatchers("/api/orders/**/status", "/api/orders/**/checkin").hasAnyRole(
                        UserRole.ROLE_ADMIN.alias(),
                        UserRole.ROLE_SUPPLIER.alias(),
                        UserRole.ROLE_DISTRIBUTOR.alias(),
                        UserRole.ROLE_MANUFACTURER.alias(),
                        UserRole.ROLE_SHIPPER.alias()
                )
                .antMatchers("/api/orders/**").authenticated()
                // Product
                .antMatchers("/api/products/**").permitAll()
                // Rating
                .antMatchers(HttpMethod.POST, "/api/ratings/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/ratings/**").authenticated()
                .antMatchers("/api/ratings/**").permitAll()
                // Delivery Schedule
                .antMatchers("/api/schedules/**").hasAnyRole(
                        UserRole.ROLE_ADMIN.alias(),
                        UserRole.ROLE_SUPPLIER.alias(),
                        UserRole.ROLE_DISTRIBUTOR.alias(),
                        UserRole.ROLE_MANUFACTURER.alias()
                )
                // Supplier
                .antMatchers("/api/suppliers/profile/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .antMatchers("/api/suppliers/product/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .antMatchers("/api/suppliers/**/rating/add").authenticated()
                .antMatchers("/api/suppliers/**").permitAll()
                // Tag
                .antMatchers("/api/tags/**").permitAll()
                // Tax
                .antMatchers("/api/taxes/**").permitAll()
                // Unit
                .antMatchers("/api/units/**").permitAll()
                // User
                .antMatchers("/api/users/profile/**").authenticated()
                .antMatchers("/api/users/confirm").authenticated()
                .antMatchers("/api/users/**").permitAll()
                .and()
                .antMatcher("/api/**").httpBasic().authenticationEntryPoint(this.restServicesEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/api/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                .and()
                .addFilterBefore(this.jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler());
    }
}
