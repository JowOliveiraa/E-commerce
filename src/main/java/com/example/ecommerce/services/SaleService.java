package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.models.orms.Sale;
import com.example.ecommerce.repositories.CustomerRepository;
import com.example.ecommerce.repositories.SaleRepository;
import com.example.ecommerce.repositories.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public ResponseEntity register(SaleDTO dto) {

        var sale = new Sale(dto);
        saleRepository.save(sale);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SaleDAO(sale));
    }

    public ResponseEntity getById(Long id) {

        if (!saleRepository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var sale = saleRepository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(sale);
    }

    public Page search(Pageable pageable, Long customer, Long seller, Long product) {

        Page<Sale> sales = null;
        List<SaleDAO> pagedSales;

        if (Objects.isNull(customer) && Objects.isNull(seller) && Objects.isNull(product)) sales = saleRepository.findAll(pageable);
        if (!Objects.isNull(customer) && Objects.isNull(seller) && Objects.isNull(product)) sales = saleRepository.findByCustomerId(pageable, customer);
        if (Objects.isNull(customer) && !Objects.isNull(seller) && Objects.isNull(product)) sales = saleRepository.findBySellerId(pageable, seller);
        if (Objects.isNull(customer) && Objects.isNull(seller) && !Objects.isNull(product)) sales = saleRepository.findByProductId(pageable, product);

        pagedSales = sales.getContent().stream().map(SaleDAO::new).collect(Collectors.toList());

        return new PageImpl<>(pagedSales, pageable, sales.getTotalElements());
    }
}
