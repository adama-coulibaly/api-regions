package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRoles,Long> {
    UserRoles findByRoleName(String rolename) ;
}
