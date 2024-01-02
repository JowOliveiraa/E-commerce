package com.shop.models.orms;

import com.shop.models.dtos.SaleDTO;
import com.shop.models.entities.Costumer;
import com.shop.models.entities.Product;
import com.shop.models.entities.Seller;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
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
    private Long costumerId;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private Integer productsQuantity;

    @Column(nullable = false)
    private Set<Long> productsId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate date;



    public Sale(SaleDTO dto) {

        this.costumerId = dto.costumerId();
        this.sellerId = dto.sellerId();
        this.productsQuantity = dto.productsQuantity();
        this.productsId = dto.productsId();
        this.price = dto.price();
        this.date = LocalDate.now();
    }

}
