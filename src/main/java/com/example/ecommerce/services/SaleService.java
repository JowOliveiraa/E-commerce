package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.models.entities.Sale;
import com.example.ecommerce.models.entities.SoldProducts;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.SaleRepository;
import com.example.ecommerce.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationService authService;


    @Transactional
    public ResponseEntity<Object> register(SaleDTO dto) {

        if (authService.notExists(dto.customerId(), "CUSTOMER")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do cliente invalido!");
        }

        for (SoldProducts soldProducts : dto.products()) {

            Long id = soldProducts.getProductId();

            if (!productRepository.existsById(id)) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do produto invalido");
            }

            var product = productRepository.getReferenceById(id);
            Integer order = soldProducts.getQuantity();

            if (!product.sufficientQuantity(order)) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body("Quantidade insuficiente do produto: " + product.getName());
            }

        }

        dto.products().forEach(soldProducts -> {

            Long id = soldProducts.getProductId();
            Integer order = soldProducts.getQuantity();

            var product = productRepository.getReferenceById(id);
            product.sold(order);

            Long sellerId = product.getSellerId();
            soldProducts.setSellerId(sellerId);

            var seller = userRepository.getReferenceById(sellerId);
            seller.sell();

        });

        var customer = userRepository.getReferenceById(dto.customerId());
        customer.purchase();

        var sale = new Sale(dto);
        repository.save(sale);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleDAO(sale));
    }
}
