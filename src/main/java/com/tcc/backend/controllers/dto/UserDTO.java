package com.tcc.backend.controllers.dto;

import com.tcc.backend.models.User;
import lombok.Getter;

@Getter
public class UserDTO {
    private String name;
    private String email;

    public UserDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
