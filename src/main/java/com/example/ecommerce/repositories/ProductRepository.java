package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findBySellerId(Pageable pageable, Long seller);

    @Query(value = "SELECT * FROM products WHERE name LIKE %:name%", nativeQuery = true)
    Page<Product> findByName(Pageable pageable,@Param(value = "name") String name);

    @Query(value = "SELECT * FROM products WHERE seller_id = :seller AND name LIKE %:name%", nativeQuery = true)
    Page<Product> findBySellerIdAndName(Pageable pageable,@Param(value = "seller") Long seller,@Param(value = "name") String name);
}
