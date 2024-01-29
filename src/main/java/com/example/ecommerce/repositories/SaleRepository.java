package com.example.ecommerce.repositories;

import com.example.ecommerce.models.orms.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByCustomerId(Pageable pageable, Long customer);

    Page<Sale> findBySellerId(Pageable pageable, Long seller);

    @Query(value = "SELECT * FROM sales JOIN products_sold ON id = sale_id WHERE id = :product", nativeQuery = true)
    Page<Sale> findByProductId(Pageable pageable,@Param(value = "product") Long product);
}
