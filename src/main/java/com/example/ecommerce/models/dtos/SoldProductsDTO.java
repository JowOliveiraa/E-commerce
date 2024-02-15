package com.example.ecommerce.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class SoldProductsDTO {

    @JsonIgnore
    @Setter
    private Long sellerId;

    private Long productId;

    private Integer quantity;
}
