package com.tcc.backend.controllers;

import com.tcc.backend.controllers.dto.UserDTO;
import com.tcc.backend.controllers.form.UserForm;
import com.tcc.backend.models.User;
import com.tcc.backend.resources.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> create(@RequestBody UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userForm.converter();
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        if (!foundUser.isPresent()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);

            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

            return ResponseEntity.created(uri).body(new UserDTO(user));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}