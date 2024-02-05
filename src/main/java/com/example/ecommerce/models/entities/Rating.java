package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.RatingDTO;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime registerAt;


    public Rating(RatingDTO dto) {

        this.productId = dto.productId();
        this.customerId = dto.customerId();
        this.rating = dto.rating();
        this.comment = dto.comment();
        this.registerAt = LocalDateTime.now();
    }
}
