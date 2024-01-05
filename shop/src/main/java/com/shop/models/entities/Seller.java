package com.shop.models.entities;

import com.shop.models.dtos.SellerDTO;
import com.shop.models.enums.UserStatus;
import com.shop.models.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "Sellers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Seller implements Serializable {

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
    private UserStatus status;

    @Column(length = 10)
    private String password;

    private Integer numberOfSales;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public Seller(SellerDTO dto) {

        this.name = dto.name();
        this.cpf = dto.cpf();
        this.email = dto.email();
        this.cellPhone = dto.cellPhone();
        this.status = UserStatus.ATIVO;
        this.numberOfSales = 0;
        this.role = Role.VENDEDOR;
    }

    public void update(SellerDTO dto) {

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

        this.status =UserStatus.ATIVO;
    }

    public void addSale() {

        this.numberOfSales += 1;
    }
}
