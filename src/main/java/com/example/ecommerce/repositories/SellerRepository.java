package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    UserDetails findByCpf(String cpf);
}
