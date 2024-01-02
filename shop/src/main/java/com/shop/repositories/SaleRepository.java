package com.shop.repositories;

import com.shop.models.orms.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByCostumerId(Pageable pageable, Long costumerId);

    Page<Sale> findBySellerId(Pageable pageable, Long sellerId);

    Page<Sale> findByCostumerIdAndSellerId(Pageable pageable, Long costumerId, Long sellerId);
}
