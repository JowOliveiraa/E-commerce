package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Customer;
import com.example.ecommerce.models.orms.Address;

public record AddressDAO(
        Long id,
        String streetAndNumber,
        String zipCode
) {
    public AddressDAO(Address address) {

        this(
                address.getId(),
                address.getStreetAndNumber(),
                address.getZipCode()
        );
    }
}
