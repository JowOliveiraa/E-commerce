package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.ProductDAO;
import com.example.ecommerce.models.daos.RatingDAO;
import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.RatingDTO;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.entities.Rating;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private AuthenticationService authService;

    private static final String role = "SELLER";

    @Transactional
    public ResponseEntity<Object> register(ProductDTO dto) {

        if (authService.notExists(dto.sellerId(), role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do vendedor invalido!");
        }

        var product = new Product(dto);
        repository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDAO(product));
    }

    @Transactional
    public ResponseEntity<Object> rating(RatingDTO dto) {

        if (!repository.existsById(dto.productId())) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do produto invalido!");
        }

        if (authService.notExists(dto.customerId(), "CUSTOMER")) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do cliente invalido!");
        }

        var product = repository.getReferenceById(dto.productId());
        product.doRating(dto.rating());

        var rating = new Rating(dto);
        ratingRepository.save(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RatingDAO(rating));
    }

    public ResponseEntity<Object> getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var product = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ProductDAO(product));
    }

    public Page<ProductDAO> search(Pageable pageable, String name, String category) {
    }
}
