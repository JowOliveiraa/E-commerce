package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByCustomerId(Pageable pageable, Long customer);

    @Query(value = "SELECT s.* FROM sales s INNER JOIN sold_products sp ON s.id = sp.sale_id WHERE sp.seller_id = ?1", nativeQuery = true)
    Page<Sale> findBySellerId(Pageable pageable, Long seller);

    @Query(value = "SELECT s.* FROM sales s INNER JOIN sold_products sp ON s.id = sp.sale_id WHERE sp.product_id = ?1", nativeQuery = true)
    Page<Sale> findByProductId(Pageable pageable, Long product);
}
