package com.tcc.backend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserScope> usersScopes = new ArrayList<>();

    public User(String name, String email, String password, String lastname) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastname = lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usersScopes;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}