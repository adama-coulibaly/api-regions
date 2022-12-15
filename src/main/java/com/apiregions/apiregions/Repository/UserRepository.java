package com.apiregions.apiregions.Repository;

import com.apiregions.apiregions.Models.UsersApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersApp, Long> {
 UsersApp findByUsername(String username) ;
}
