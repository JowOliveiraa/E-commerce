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

    @Query(value = "SELECT * FROM products WHERE name LIKE %:name%", nativeQuery = true)
    Page<Product> searchName(Pageable pageable,@Param("name") String name);

    @Query(value = "SELECT * FROM products WHERE name LIKE %:name% AND category = :category", nativeQuery = true)
    Page<Product> searchNameAndCategory(Pageable pageable,@Param("name") String name,@Param("category") String category);

    @Query(value = "SELECT * FROM products WHERE category = ?2 AND seller_id = ?3 AND name LIKE %?1%", nativeQuery = true)
    Page<Product> searchAll(Pageable pageable, String name, String category, Long seller);

    @Query(value = "SELECT * FROM products WHERE name LIKE %?1% AND seller_id = ?2", nativeQuery = true)
    Page<Product> searchNameAndSeller(Pageable pageable, String name, Long seller);

    @Query(value = "SELECT * FROM products WHERE category = ?1", nativeQuery = true)
    Page<Product> searchCategory(Pageable pageable, String category);

    @Query(value = "SELECT * FROM products WHERE category = ?1 AND seller_id = ?2", nativeQuery = true)
    Page<Product> searchCategoryAndSeller(Pageable pageable, String category, Long seller);

    @Query(value = "SELECT * FROM products WHERE seller_id = ?1", nativeQuery = true)
    Page<Product> searchSeller(Pageable pageable, Long seller);
}
