package com.apiregions.apiregions;

import com.apiregions.apiregions.Configuration.JwtAuthentificationFilter;
import com.apiregions.apiregions.Configuration.JwtAuthorizationFilter;
import com.apiregions.apiregions.Models.UsersApp;
import com.apiregions.apiregions.Sevices.AccountServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AccountServices accountServices;
//ICI MON INJECTION DE DEPENDANCES
    public SecurityConfig(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @Override
    protected  void configure(AuthenticationManagerBuilder Auth) throws Exception{
        Auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
               UsersApp usersApp = accountServices.loadUsersByUsername(username); // ICI ON RECUPERRE UN UTILISATEUR
                //ICI ON CREE UNE COLLECTION DE ROLE
                Collection<GrantedAuthority> Authotities = new ArrayList<>();
                // NOUS ALLONS AJOUTER LES ROLES TROUVES

                usersApp.getUserRoles().forEach(R -> {
                   Authotities.add( new SimpleGrantedAuthority(R.getRoleName()));
                });


               //ICI ON RETOURNE NOTRE UTILISATEUR
                return new User(usersApp.getUsername(),usersApp.getPassword(),Authotities);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();  // ICI ON DESCTIVE LES SECURITES CSRF
        http.headers().frameOptions().disable();
        http.authorizeHttpRequests().antMatchers("/refreshToken/**").permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin();
        http.authorizeHttpRequests().anyRequest().authenticated();  // ICI ON GERE TOUTE LES PERMISSIONS
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
