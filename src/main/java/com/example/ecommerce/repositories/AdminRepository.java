package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    UserDetails findByCpf(String cpf);
}
