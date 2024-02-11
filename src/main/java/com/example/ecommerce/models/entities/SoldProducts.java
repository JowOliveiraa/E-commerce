package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.SoldProductsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "sold_products")
public class SoldProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Setter
    @Column(nullable = false)
    private Long sellerId;


    public SoldProducts(SoldProductsDTO dto) {

        this.productId = dto.getProductId();
        this.quantity = dto.getQuantity();
        this.sellerId = dto.getSellerId();
    }
}
