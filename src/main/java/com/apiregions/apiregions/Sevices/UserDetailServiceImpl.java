package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Models.UsersApp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private AccountServices accountServices;

    public UserDetailServiceImpl(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

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
}
