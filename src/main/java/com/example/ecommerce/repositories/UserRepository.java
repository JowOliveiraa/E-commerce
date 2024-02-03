package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByCpf(String cpf);

    @Query(value = "SELECT * FROM users WHERE role = :role AND status = :status AND (name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<User> findByNameOrCpf(Pageable pageable,@Param("role") String role,@Param("status") String status,@Param("search") String search);

    @Query(value = "SELECT * FROM users WHERE role = :role AND status = :status", nativeQuery = true)
    Page<User> find(Pageable pageable,@Param("role") String role,@Param("status") String status);

    @Query(value = "SELECT * FROM users WHERE id = :id AND role = :role", nativeQuery = true)
    User validateRole(@Param("id") Long id,@Param("role") String role);
}

