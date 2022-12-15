package com.apiregions.apiregions.Configuration;

import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;
import com.apiregions.apiregions.Sevices.AccountServices;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    private AccountServices accountServices;
// CETTE CONSTRUCTEUR NOUS PERMET DE FAIRE L'INJECTION DE DEPENDANCE
    public AccountRestController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    // ICI ON LISTE TOUT LES UTILISATEURS
    @GetMapping(path = "/utilisateurs")
    public List<UsersApp> utilisateurs(){
        return accountServices.listerUsers();
    }

    // ICI ON AJOUTE UN UTILISATEURS
    @PostMapping(path = "/addusers")
    public String ajouterUtilisateurs(@RequestBody UsersApp usersApp){
        return accountServices.addNewUsers(usersApp);
    }

    // ICI ON AJOUTE UN ROLE
    @PostMapping(path = "/addroles")
    public String ajouterRoles(@RequestBody UserRoles userRoles){
        return accountServices.addNewRoles(userRoles);
    }

    //ICI ON ATTRIBUT UN ROLE A UN UTILISATEUR SPECIFIQUE
    @PostMapping(path = "/addroletousers")
    public String attribuerUnRole(@RequestBody AddRoleForm addRoleForm){

      return   accountServices.addRoleToUser(addRoleForm.getUsername(),addRoleForm.getRolename());
    }

    // ICI ON RECUPERE UN UTILISATEUR PAR SON USERNAME
    @GetMapping(path = "/user/{username}")
    public UsersApp unUser(@PathVariable("username") String username){
       return accountServices.loadUsersByUsername(username);

    }

}

// CETTE CLASSE NOUS SERT LORS LA CREATION OU ATTRIBUTION DE ROLE A UN UTILISATEURS
@Data
class AddRoleForm{
    private String username;
    private String rolename;
}
