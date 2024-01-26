package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.AdminDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User{

    public Admin(AdminDTO dto) {
        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Role.ADMIN);
    }

    public void update(UpdateDTO dto) {

        update(dto.name(), dto.email());
    }
}
