package com.tcc.backend.controllers.form;

import com.tcc.backend.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserForm {
    private String name;
    private String lastname;
    private String email;
    private String password;

    public User converter() {
        return new User(this.name, this.email, this.password, this.lastname);
    }
}
