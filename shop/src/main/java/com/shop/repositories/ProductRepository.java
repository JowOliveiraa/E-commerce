package com.shop.repositories;

import com.shop.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    Page<Product> findByAvailable(Pageable pageable);

    @Query(value = "SELECT * FROM Products WHERE (quantity > 0) AND name LIKE %:name%", nativeQuery = true)
    Page<Product> findByAvailableNames(Pageable pageable,@Param(value = "name") String name);

    @Query(value = "SELECT * FROM Products WHERE (quantity  > 0 AND category = :category) AND name LIKE %:name%", nativeQuery = true)
    Page<Product> findByAvailableNamesAndCategories(Pageable pageable,@Param(value = "name") String name,@Param(value = "category") String category);

    @Query(value = "SELECT * FROM Products WHERE (quantity > 0) AND category = :category", nativeQuery = true)
    Page<Product> findByAvailableCategory(Pageable pageable,@Param(value = "category") String category);

    @Query(value = "SELECT * FROM Products WHERE (quantity = 0) AND name LIKE %:name%",nativeQuery = true)
    Page<Product> findByNotAvailableNames(Pageable pageable,@Param(value = "name") String name);

    @Query(value = "SELECT * FROM Products WHERE (quantity = 0 AND category = :category) AND name LIKE %:name%", nativeQuery = true)
    Page<Product> findByNotAvailableNamesAndCategories(Pageable pageable,@Param(value = "name") String name,@Param(value = "category") String category);

    @Query(value = "SELECT * FROM Products WHERE (quantity = 0) AND category = :category", nativeQuery = true)
    Page<Product> findByNotAvailableCategory(Pageable pageable,@Param(value = "category") String category);
}
