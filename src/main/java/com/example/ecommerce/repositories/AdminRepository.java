package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByCpf(String cpf);

    @Query(value = "SELECT * FROM admins WHERE status = :status", nativeQuery = true)
    Page<Admin> findByStatus(Pageable pageable,@Param(value = "status") String status);

    @Query(value = "SELECT * FROM admins WHERE status = :status AND (name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<Admin> search(Pageable pageable, String status, String search);
}
