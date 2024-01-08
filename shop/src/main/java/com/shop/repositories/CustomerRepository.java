package com.shop.repositories;

import com.shop.models.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCpf(String cpf);

    Object getReferenceByCpf(String cpf);

    @Query(value = "SELECT * FROM Customers WHERE status=:status AND (name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<Customer> search(Pageable pageable, @Param("search") String search, @Param("status") String status);
}
