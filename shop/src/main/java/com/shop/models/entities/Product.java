package com.shop.models.entities;

import com.shop.models.dtos.ProductDTO;
import com.shop.models.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Products")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;

    public Product(ProductDTO dto) {
        this.name = dto.name();
        this.price = dto.price();
        this.quantity = dto.quantity();
        this.category = dto.category();
    }

    public void update(ProductDTO dto) {
        if (!Objects.equals(dto.name(), this.name)) this.name = dto.name();
        if (!Objects.equals(dto.price(), this.price)) this.price = dto.price();
        if (!Objects.equals(dto.quantity(), this.quantity)) this.quantity = dto.quantity();
        if (!Objects.equals(dto.category(), this.category)) this.category = dto.category();
    }
}
