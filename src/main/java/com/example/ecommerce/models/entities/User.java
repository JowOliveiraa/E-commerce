package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

// standard attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime registerAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

// seller attributes

    private Integer numberOfSales;

// customer attributes

    private Integer numberOfPurchases;


// standard methods
    public User(RegisterDTO dto, Role role) {

        this.cpf = dto.cpf();
        this.name = dto.name();
        this.email = dto.email();
        this.password = dto.password();
        this.role = role;
        this.status = Status.ACTIVE;
        this.registerAt = LocalDateTime.now();
        this.numberOfSales = 0;
        this.numberOfPurchases = 0;
    }

    public void update(UpdateDTO dto) {

        this.name = dto.name();
        this.email = dto.email();
    }


// authentication methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == Role.ADMIN) {

            return List.of(

                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_SELLER"),
                    new SimpleGrantedAuthority("ROLE_CUSTOMER")
            );

        } else if (this.role == Role.SELLER) {

            return List.of(new SimpleGrantedAuthority("ROLE_SELLER"));

        } else {

            return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }
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