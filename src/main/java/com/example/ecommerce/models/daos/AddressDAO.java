package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Address;

public record AddressDAO(
        Long id,
        Long userId,
        String street,
        String number,
        String zipCode
) {

    public AddressDAO(Address address) {

        this(
                address.getId(),
                address.getUserId(),
                address.getStreet(),
                address.getNumber(),
                address.getZipCode()
        );
    }
}
