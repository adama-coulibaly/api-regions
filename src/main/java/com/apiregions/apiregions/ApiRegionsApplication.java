package com.apiregions.apiregions;

import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;
import com.apiregions.apiregions.Repository.PaysRepository;
import com.apiregions.apiregions.Sevices.AccountServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApiRegionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRegionsApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner Start(AccountServices accountServices, PaysRepository paysRepository){
	return args -> {
		// ICI MES ROLES
		accountServices.addNewRoles(new UserRoles(null,"ADMIN"));
		accountServices.addNewRoles(new UserRoles(null,"USER"));

		//ICI MES UTILISATEURS
		accountServices.addNewUsers(new UsersApp(null,"adama","adama@gmail.com","1234",new ArrayList<>()));
		accountServices.addNewUsers(new UsersApp(null,"awa","awa@gmail.com","1234",new ArrayList<>()));

		// ICI ATRIBUTION DE ROLE A UN UTILISATEURS

		accountServices.addRoleToUser("adama","ADMIN");
		accountServices.addRoleToUser("awa","USER");


	};
	}

}
