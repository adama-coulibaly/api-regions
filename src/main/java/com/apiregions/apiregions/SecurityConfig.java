package com.apiregions.apiregions;

import com.apiregions.apiregions.Configuration.JwtAuthentificationFilter;
import com.apiregions.apiregions.Configuration.JwtAuthorizationFilter;
import com.apiregions.apiregions.Sevices.AccountServices;
import com.apiregions.apiregions.Sevices.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AccountServices accountServices;
//ICI MON INJECTION DE DEPENDANCES
  private UserDetailServiceImpl userDetailService;

    public SecurityConfig(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected  void configure(AuthenticationManagerBuilder Auth) throws Exception{
    Auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();  // ICI ON DESCTIVE LES SECURITES CSRF
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests()
                .antMatchers("/refreshToken/**").permitAll()
                .antMatchers("/projet/odk/Regions/**").permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin();
        http.authorizeHttpRequests().anyRequest().permitAll();  // ICI ON GERE TOUTE LES PERMISSIONS
       // ICI ON AJOUTE NOTRE FILTRE
        http.addFilter(new JwtAuthentificationFilter(authenticationManagerBean()));

        // ICI ON AJOUTE NOTRE FILTRE D'AUTHORIZATION
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    // CETTE CLASS NOUS PERMET DE POUBOIR UTILISER NOTRE FILTRE
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
