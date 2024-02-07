package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.models.entities.Sale;
import com.example.ecommerce.repositories.SaleRepository;
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
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;


    @Transactional
    public ResponseEntity<Object> register(SaleDTO dto) {



        var sale = new Sale(dto);

        repository.save(sale);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleDAO(sale));
    }
}
