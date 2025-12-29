package io.github.aplaraujo.tdd_auth_empregados_exercicio.controllers;

import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.AuthRequest;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.entities.User;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.JwtService;
import io.github.aplaraujo.tdd_auth_empregados_exercicio.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserDetailsServiceImpl service;
    private final JwtService jwtService;
    private final AuthenticationManager manager;

    @PostMapping("/new-user")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        try {
            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            System.out.println("=== AUTENTICAÇÃO OK ===");
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getEmail());
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
