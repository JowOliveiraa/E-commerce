package com.shop.repositories;

import com.shop.models.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(value = "SELECT * FROM Sellers WHERE status=:status AND(name LIKE %:search% OR cpf LIKE %:search%)", nativeQuery = true)
    Page<Seller> search(Pageable pageable, @Param(value = "search") String search, @Param(value = "status") String status);
}
