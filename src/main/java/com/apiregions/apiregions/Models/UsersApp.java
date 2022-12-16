package com.apiregions.apiregions.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String username;
    private String useremail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;




    @ManyToMany(fetch = FetchType.EAGER)
    Collection<UserRoles> userRoles = new ArrayList<>();


}
