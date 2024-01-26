package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCpf(String cpf);

    @Query(value = "SELECT * FROM customers WHERE status = :status", nativeQuery = true)
    Page<Customer> findByStatus(@Param(value = "status") String status, Pageable pageable);

    @Query(value = "SELECT * FROM customers WHERE status = :status AND(name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<Customer> search(@Param(value = "status") String status, @Param(value = "search") String search, Pageable pageable);
}
