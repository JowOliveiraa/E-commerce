package com.shop.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.models.dtos.CostumerDTO;
import com.shop.models.enums.UserStatus;
import com.shop.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "Costumers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Costumer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String cellPhone;

    public Integer quantityOfPurchases;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(length = 10)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt;

    public Costumer(CostumerDTO dto) {

        this.name = dto.name();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.cellPhone = dto.cellPhone();
        this.quantityOfPurchases = 0;
        this.status = UserStatus.ATIVO;
        this.role = Role.CLIENTE;
        this.createdAt = LocalDateTime.now();
    }

    public void update(CostumerDTO dto) {

        if (!Objects.equals(dto.name(), this.name)) this.name = dto.name();
        if (!Objects.equals(dto.cpf(), this.cpf)) this.cpf = dto.cpf();
        if (!Objects.equals(dto.email(), this.email)) this.email = dto.email();
        if (!Objects.equals(dto.cellPhone(), this.cellPhone)) this.cellPhone = dto.cellPhone();
    }

    public void updatePassword(String password) {

        this.password = password;
    }

    public void inactive() {

        this.status = UserStatus.INATIVO;
    }

    public void active() {

        this.status = UserStatus.ATIVO;
    }

    public void addPurchase() {

        this.quantityOfPurchases += 1;
    }
}
