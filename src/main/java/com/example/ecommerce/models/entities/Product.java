package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.models.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Integer sales;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false)
    private Integer numberOfRatings;

    @Column(nullable = false)
    private Integer totalRating;

    @Column(nullable = false)
    private LocalDateTime registerAt;


    public Product(ProductDTO dto) {

        this.sellerId = dto.sellerId();
        this.name = dto.name();
        this.quantity = dto.quantity();
        this.price = dto.price();
        this.category = dto.category();
        this.sales = 0;
        this.rating = BigDecimal.ZERO;
        this.numberOfRatings = 0;
        this.totalRating = 0;
        this.registerAt = LocalDateTime.now();
    }

    public void doRating(Integer rating) {

        this.numberOfRatings += 1;
        this.totalRating += rating;
        this.rating = BigDecimal.valueOf((double) totalRating / numberOfRatings);
    }

    public void update(UpdateProductDTO dto) {

        this.name = dto.name();
        this.price = dto.price();
        this.category = dto.category();
        this.quantity = dto.quantity();
    }

    public boolean sufficientQuantity(Integer order) {

        return this.quantity >= order;
    }

    public void sold(Integer order) {

        this.quantity -= order;
        this.sales += order;
    }
}
