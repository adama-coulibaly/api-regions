package com.apiregions.apiregions.Sevices;

import com.apiregions.apiregions.Models.Pays;
import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;

import java.util.List;

public interface AccountServices {
    String addNewUsers(UsersApp usersApp);
    String addNewRoles(UserRoles userRoles);
    String addRoleToUser(String username,String rolename);
    UsersApp loadUsersByUsername(String username);
    List<UsersApp> listerUsers();


}
