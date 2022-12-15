package com.apiregions.apiregions.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
// INJECTION
    public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // QUANT L'UTILISATEUR VA ESSAYER DE SE CONNECTER
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username  = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken AuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(AuthenticationToken);
    }

    // UNE FOIS QUE LA CONNECTIO EST REUSSISSE
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User)authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("MonSecret");
        //GENERATION DE TOKEN

        String JwtAccessToken = JWT.create()
                .withSubject(user.getUsername())  // Le nom de suser pour generer le token
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.EXPIRE_TOKEN)) // Durée de mon token
                .withIssuer(request.getRequestURL().toString()) // LE NOM DE L'APPLICATION UTILISER
                .withClaim("roles",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm); // SIGNATURE


        String RefreshtAccessToken = JWT.create()
                .withSubject(user.getUsername())  // Le nom de suser pour generer le token
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtUtil.REFRESH_TOKEN)) // Durée de mon token
                .withIssuer(request.getRequestURL().toString()) // LE NOM DE L'APPLICATION UTILISER
               .sign(algorithm); // SIGNATURE

        Map<String,String> IdToken = new HashMap<>();
        IdToken.put("Access-Token",JwtAccessToken);
        IdToken.put("Refresh-Token",RefreshtAccessToken);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),IdToken);
    }
}
