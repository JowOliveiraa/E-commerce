package com.example.ecommerce.repositories;

import com.example.ecommerce.models.daos.RatingDAO;
import com.example.ecommerce.models.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findByProductId(Pageable pageable, Long product);
}
