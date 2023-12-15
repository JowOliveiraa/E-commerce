package com.shop.models.entities;

import com.shop.models.dtos.CostumerDTO;
import com.shop.models.enums.CostumerStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
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

    @Enumerated(EnumType.STRING)
    private CostumerStatus status;

    @Column(length = 10)
    private String password;

    public Costumer(CostumerDTO dto) {

        this.name = dto.name();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.cellPhone = dto.cellPhone();
        this.status = CostumerStatus.ATIVO;
    }

    public void update(CostumerDTO dto) {

        if (!Objects.equals(dto.name(), this.name)) this.name = dto.name();
        if (!Objects.equals(dto.cpf(), this.cpf)) this.cpf = dto.cpf();
        if (!Objects.equals(dto.email(), this.email)) this.email = dto.email();
        if (!Objects.equals(dto.cellPhone(), this.cellPhone)) this.cellPhone = dto.cellPhone();
        if (!Objects.equals(dto.status(), this.status)) this.status = dto.status();
    }
}
