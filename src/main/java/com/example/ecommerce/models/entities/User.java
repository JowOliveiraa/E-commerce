package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 50)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Timestamp
    private LocalDateTime registerAt;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;


    public User(String name, String cpf, String email, String password, Role role) {

        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registerAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    public void update(String name, String email) {

        this.name = name;
        this.email = email;
    }
}
