package com.example.ecommerce.models.orms;

import com.example.ecommerce.models.entities.Customer;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @Column(nullable = false, length = 50)
    private String StreetAndNumber;

    @Column(nullable = false, length = 10)
    private String zipCode;
}
