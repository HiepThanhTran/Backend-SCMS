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
                .antMatchers(HttpMethod.DELETE, "/api/user/**/delete").hasRole(UserRole.ROLE_ADMIN.alias())
                .antMatchers(HttpMethod.DELETE, "/api/supplier/**/details").hasRole(UserRole.ROLE_ADMIN.alias())
                .antMatchers(HttpMethod.POST, "/api/user/confirm").authenticated()
                .antMatchers("/api/user/profile/**").authenticated()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/supplier/list").permitAll()
                .antMatchers(HttpMethod.GET, "/api/supplier/**/details").permitAll()
                .antMatchers(HttpMethod.GET, "/api/supplier/**/rating").permitAll()
                .antMatchers(HttpMethod.POST, "/api/supplier/**/rating/add").authenticated()
                .antMatchers("/api/supplier/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .and()
                .antMatcher("/api/**").httpBasic().authenticationEntryPoint(this.restServicesEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//                .antMatchers("/api/supplier/**").hasRole(UserRole.ROLE_SUPPLIER.alias())
                .and()
                .addFilterBefore(this.jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(this.customAccessDeniedHandler());
    }
}
