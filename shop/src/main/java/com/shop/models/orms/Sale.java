package com.shop.models.orms;

import com.shop.models.dtos.SaleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "Sales")
public class Sale implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Integer productsQuantity;

    @Column(nullable = false)
    private Set<Long> productsId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime date;



    public Sale(SaleDTO dto) {

        this.customerId = dto.customerId();
        this.sellerId = dto.sellerId();
        this.productsQuantity = dto.productsQuantity();
        this.productsId = dto.productsId();
        this.price = dto.price();
        this.date = LocalDateTime.now();
    }

}
