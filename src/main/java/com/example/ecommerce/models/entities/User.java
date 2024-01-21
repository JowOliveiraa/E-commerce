package com.example.ecommerce.models.entities;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.enums.Status;
import jakarta.persistence.*;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

@MappedSuperclass
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;


    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(nullable = false)
    private SimpleDateFormat registerAt;


    public User(String name, String cpf, String email, String password, Roles role) {

        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = Status.ACTIVE;
        this.registerAt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Roles.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_SELLER"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }

        if (this.role == Roles.SELLER) {
            return List.of(new SimpleGrantedAuthority("ROLE_SELLER"));
        }

        else {
            return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
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

