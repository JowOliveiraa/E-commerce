package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.models.dtos.SoldProductsDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private LocalDateTime registerAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "sale_id" )
    private List<SoldProducts> products;

    public Sale(SaleDTO dto) {

        this.customerId = dto.customerId();
        this.products = dto.products().stream().map(SoldProducts::new).collect(Collectors.toList());
        this.registerAt = LocalDateTime.now();
    }
}
