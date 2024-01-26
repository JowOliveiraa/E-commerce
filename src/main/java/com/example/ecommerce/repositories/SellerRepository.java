package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByCpf(String cpf);

    @Query(value = "SELECT * FROM sellers WHERE status = :status", nativeQuery = true)
    Page<Seller> findByStatus(Pageable pageable,@Param(value = "status") String status);

    @Query(value = "SELECT * FROM sellers WHERE status = :status AND (name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<Seller> search(Pageable pageable,@Param(value = "status") String status,@Param(value = "search") String search);
}
