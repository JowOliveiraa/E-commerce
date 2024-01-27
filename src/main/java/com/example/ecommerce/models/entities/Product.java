package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.models.enums.Category;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
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
    private BigDecimal price;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private Integer numberOfRatings;

    @Column(nullable = false)
    private Integer totalRating;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer quantityOfSales;

    @Column(nullable = false)
    @Timestamp
    private LocalDateTime registerAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;



    public Product(ProductDTO dto) {

        this.sellerId = dto.sellerId();
        this.name = dto.name();
        this.price = dto.price();
        this.category = dto.category();
        this.quantity = dto.quantity();
        this.quantityOfSales = 0;
        this.rating = 0;
        this.numberOfRatings = 0;
        this.totalRating = 0;
        this.registerAt = LocalDateTime.now();
    }

    public void update(UpdateProductDTO dto) {

        this.name = dto.name();
        this.price = dto.price();
        this.category = dto.category();
        this.quantity = dto.quantity();
    }

    public boolean sold(Integer quantity) {

        if (this.quantity < quantity) {

            return false;
        }
        this.quantityOfSales += 1;
        this.quantity -= quantity;

        return true;
    }

    public void rating(Integer rating) {

        this.numberOfRatings += 1;
        this.totalRating += rating;
        this.rating = (float) this.totalRating / this.numberOfRatings;
    }

}
