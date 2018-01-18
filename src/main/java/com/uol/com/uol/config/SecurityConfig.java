package com.uol.com.uol.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/").hasRole("ADMIN")
//                .anyRequest().authenticated();

        // @formatter:off
        http
                // other config goes here...
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry());
        // @formatter:on
    }

    @Bean
    @SuppressWarnings("unchecked")
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
    }

}
