package com.tcc.backend.controllers;

import com.tcc.backend.controllers.dto.AuthenticationDTO;
import com.tcc.backend.controllers.form.AuthenticationForm;
import com.tcc.backend.resources.UserRepository;
import com.tcc.backend.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticate(@RequestBody AuthenticationForm form) {
        UsernamePasswordAuthenticationToken authData = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

        try {
            authenticationManager.authenticate(authData);
            String token = tokenService.create(authData);
            return ResponseEntity.ok(new AuthenticationDTO(token, "Bearer"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}