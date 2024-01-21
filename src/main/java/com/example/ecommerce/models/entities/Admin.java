package com.example.ecommerce.models.entities;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.dtos.AdminDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin(AdminDTO dto) {
        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Roles.ADMIN);
    }
}
