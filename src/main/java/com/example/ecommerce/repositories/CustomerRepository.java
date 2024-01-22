package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    UserDetails findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    Customer getReferenceByCpf(String cpf);
}
