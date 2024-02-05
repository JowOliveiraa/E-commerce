package com.example.ecommerce.models.dtos;

public record UpdateAddressDTO(
        String street,
        String number,
        String zipCode
) {
}
