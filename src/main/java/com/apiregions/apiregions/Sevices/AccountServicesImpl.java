package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Models.Pays;
import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;
import com.apiregions.apiregions.Repository.PaysRepository;
import com.apiregions.apiregions.Repository.RoleRepository;
import com.apiregions.apiregions.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServicesImpl implements AccountServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private PaysRepository paysRepository;

    @Override
    public String addNewUsers(UsersApp usersApp) {
        usersApp.setPassword(passwordEncoder.encode(usersApp.getPassword()));

        if (userRepository.findByUsername(usersApp.getUsername()) != null){
            return "Utilisateur existe déjà";
        }
        else{
            userRepository.save(usersApp);
            return "Utilisateur ajouté avec success";

        }

    }

    @Override
    public String addNewRoles(UserRoles userRoles) {
        if(roleRepository.findByRoleName(userRoles.getRoleName()) != null){
            return "Ce role existe déjà";
        }
         roleRepository.save(userRoles);
         return "Role ajouter avec succes";
    }

    @Override
    public String addRoleToUser(String username, String rolename) {
    UsersApp  userapp = userRepository.findByUsername(username);
    UserRoles userrole = roleRepository.findByRoleName(rolename);

        userapp.getUserRoles().add(userrole);
        return "Role attribuer avec succes";
 /*
      if(userapp.getUserRoles() == null){
        return "Role déja attribué";
    }
    else
        { userapp.getUserRoles().add(userrole);
            return "Role attribuer avec succes";

        }
   */   }



    @Override
    public UsersApp loadUsersByUsername(String username) {
        return  userRepository.findByUsername(username);
    /*    if(userRepository.findByUsername(username) == null){
            return "Utilisateur non touvé";
        }
        else {
            return  userRepository.findByUsername(username).toString();

        }

     */

    }

    @Override
    public List<UsersApp> listerUsers() {
        return userRepository.findAll();
    }


}
