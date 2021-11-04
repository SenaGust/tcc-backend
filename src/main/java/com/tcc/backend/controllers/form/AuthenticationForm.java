package com.tcc.backend.controllers.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AuthenticationForm {
    private String email;
    private String password;
}
