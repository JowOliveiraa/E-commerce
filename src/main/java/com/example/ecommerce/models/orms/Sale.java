package com.example.ecommerce.models.orms;

import com.example.ecommerce.models.dtos.SaleDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "sales")
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long sellerId;

    @ElementCollection
    @CollectionTable(name = "products_sold", joinColumns = @JoinColumn(name = "sale_id"))
    @Column(nullable = false)
    private List<ProductsSold> products;

    @Column(nullable = false)
    private LocalDateTime registerAt;

    public Sale(SaleDTO dto) {

        this.customerId = dto.customerId();
        this.sellerId = dto.sellerId();
        this.products = dto.products();
        this.registerAt = LocalDateTime.now();
    }
}
