package com.apiregions.apiregions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();  // ICI ON DESCTIVE LES SECURITES CSRF
        http.headers().frameOptions().disable();
        http.formLogin();
        http.authorizeHttpRequests().anyRequest().authenticated();  // ICI ON GERE TOUTE LES PERMISSIONS


    }

}
