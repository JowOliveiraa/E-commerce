package com.example.ecommerce.models.orms;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Embeddable
public class ProductsSold{

    private Long productId;

    private Integer quantity;

}
