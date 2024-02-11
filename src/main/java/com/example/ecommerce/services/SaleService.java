package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.models.dtos.SoldProductsDTO;
import com.example.ecommerce.models.entities.Sale;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.SaleRepository;
import com.example.ecommerce.repositories.UserRepository;
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

        for (SoldProductsDTO soldProducts : dto.products()) {

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

    public ResponseEntity<Object> getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var sale = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new SaleDAO(sale));
    }

    public Page<SaleDAO> search(Pageable pageable, Long customer, Long seller, Long product) {

        Page<Sale> pagedSales = null;

        if (Objects.isNull(customer) && Objects.isNull(seller) && Objects.isNull(product)) pagedSales = repository.findAll(pageable);
        if (!Objects.isNull(customer) && Objects.isNull(seller) && Objects.isNull(product)) pagedSales = repository.findByCustomerId(pageable, customer);
        if (Objects.isNull(customer) && !Objects.isNull(seller) && Objects.isNull(product)) pagedSales = repository.findBySellerId(pageable, seller);
        if (Objects.isNull(customer) && Objects.isNull(seller) && !Objects.isNull(product)) pagedSales = repository.findByProductId(pageable, product);

        List<SaleDAO> sales = pagedSales.getContent().stream().map(SaleDAO::new).collect(Collectors.toList());

        return new PageImpl<>(sales, pageable, pagedSales.getTotalElements());
    }
}
