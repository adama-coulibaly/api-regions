package com.apiregions.apiregions.Controller;

import com.apiregions.apiregions.Configuration.JwtUtil;
import com.apiregions.apiregions.Models.UserRoles;
import com.apiregions.apiregions.Models.UsersApp;
import com.apiregions.apiregions.Sevices.AccountServices;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AccountRestController {
    private AccountServices accountServices;
// CETTE CONSTRUCTEUR NOUS PERMET DE FAIRE L'INJECTION DE DEPENDANCE
    public AccountRestController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    // ICI ON LISTE TOUT LES UTILISATEURS


    @GetMapping(path = "/utilisateurs")
    @PostAuthorize("hasAuthority('ADMIN')")
    public List<UsersApp> utilisateurs(){
        return accountServices.listerUsers();
    }

    // ICI ON AJOUTE UN UTILISATEURS
    @PostMapping(path = "/addusers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String ajouterUtilisateurs(@RequestBody UsersApp usersApp){
        return accountServices.addNewUsers(usersApp);
    }

    // ICI ON AJOUTE UN ROLE

    @PostMapping(path = "/addroles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String ajouterRoles(@RequestBody UserRoles userRoles){
        return accountServices.addNewRoles(userRoles);
    }

    //ICI ON ATTRIBUT UN ROLE A UN UTILISATEUR SPECIFIQUE
    @PostMapping(path = "/addroletousers")
    @PostAuthorize("hasAuthority('ADMIN')")
    public String attribuerUnRole(@RequestBody AddRoleForm addRoleForm){

      return   accountServices.addRoleToUser(addRoleForm.getUsername(),addRoleForm.getRolename());
    }

    // ICI ON RECUPERE UN UTILISATEUR PAR SON USERNAME
    @GetMapping(path = "/user/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UsersApp unUser(@PathVariable("username") String username){
       return accountServices.loadUsersByUsername(username);

    }

    // REFRESH TOKEN TEST

    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String auToToken = request.getHeader(JwtUtil.AUTHORIZATION);
        if (auToToken != null && auToToken.startsWith(JwtUtil.PREFIX)) {
            try {
                String refreshToken = auToToken.substring(JwtUtil.PREFIX.length());
                Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                // ICI NOUS ALLONS VERIFIER LE TOKEN
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                UsersApp usersApp = accountServices.loadUsersByUsername(username);

                String JwtAccessToken = JWT.create()
                        .withSubject(usersApp.getUsername())  // Le nom de suser pour generer le token
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRE_TOKEN)) // Durée de mon token
                        .withIssuer(request.getRequestURL().toString()) // LE NOM DE L'APPLICATION UTILISER
                        .withClaim("roles", usersApp.getUserRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm); // SIGNATURE


                Map<String, String> IdToken = new HashMap<>();
                IdToken.put("Access-Token", JwtAccessToken);
                IdToken.put("Refresh-Token", refreshToken);

                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), IdToken);

            } catch (Exception e) {
                response.setHeader("Error-message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);

            }
        } else {
            throw new RuntimeException("Refresh token required");
        }
    }

    @GetMapping(path = "/profile")
    public UsersApp Getprofile(Principal principal){
    return accountServices.loadUsersByUsername(principal.getName());
    }
}


// CETTE CLASSE NOUS SERT LORS LA CREATION OU ATTRIBUTION DE ROLE A UN UTILISATEURS
@Data
class AddRoleForm{
    private String username;
    private String rolename;
}
