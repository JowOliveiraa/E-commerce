package com.example.ecommerce.repositories;

import com.example.ecommerce.models.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    boolean existsByUserId(Long aLong);

    @Query(value = "SELECT * FROM addresses WHERE street LIKE %:search% OR zip_code LIKE %:search%",nativeQuery = true)
    Page<Address> search(Pageable pageable,@Param("search") String search);
}
