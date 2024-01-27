package com.example.ecommerce.models.orms;

import com.example.ecommerce.models.dtos.RatingDTO;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false, length = 50)
    private String customer;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Timestamp
    private LocalDateTime registerAt;


    public Rating(RatingDTO dto, Long productId, String name) {

        this.customer = name;
        this.productId = productId;
        this.rating = dto.rating();
        this.comment = dto.comment();
        this.registerAt = LocalDateTime.now();
    }

}
