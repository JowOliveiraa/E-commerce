package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.ProductDAO;
import com.example.ecommerce.models.daos.RatingDAO;
import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.RatingDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.entities.Rating;
import com.example.ecommerce.repositories.ProductRepository;
import com.example.ecommerce.repositories.RatingRepository;
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

        if (dto.rating() > 5 || dto.rating() <= 0) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A nota de avaliação deve ser de 1 a 5!");
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

    public Page<ProductDAO> search(Pageable pageable, String name, String category, Long seller) {

        Page<Product> pagedProducts = null;

        if (Objects.isNull(name) && Objects.isNull(category) && Objects.isNull(seller)) pagedProducts = repository.findAll(pageable);
        if (!Objects.isNull(name) && Objects.isNull(category) && Objects.isNull(seller)) pagedProducts = repository.searchName(pageable, name);
        if (!Objects.isNull(name) && !Objects.isNull(category) && Objects.isNull(seller)) pagedProducts = repository.searchNameAndCategory(pageable, name, category);
        if (!Objects.isNull(name) && !Objects.isNull(category) && !Objects.isNull(seller)) pagedProducts = repository.searchAll(pageable, name, category, seller);
        if (!Objects.isNull(name) && Objects.isNull(category) && !Objects.isNull(seller)) pagedProducts = repository.searchNameAndSeller(pageable, name, seller);
        if (Objects.isNull(name) && !Objects.isNull(category) && Objects.isNull(seller)) pagedProducts = repository.searchCategory(pageable, category);
        if (Objects.isNull(name) && !Objects.isNull(category) && !Objects.isNull(seller)) pagedProducts = repository.searchCategoryAndSeller(pageable, category, seller);
        if (Objects.isNull(name) && Objects.isNull(category) && !Objects.isNull(seller)) pagedProducts = repository.searchSeller(pageable, seller);


        List<ProductDAO> products = pagedProducts.getContent().stream().map(ProductDAO::new).collect(Collectors.toList());

        return new PageImpl<>(products, pageable, pagedProducts.getTotalElements());
    }

    public Page<RatingDAO> getRatings(Pageable pageable, Long product) {

        Page<Rating> pagedRatings = ratingRepository.findByProductId(pageable, product);
        List<RatingDAO> ratings = pagedRatings.getContent().stream().map(RatingDAO::new).collect(Collectors.toList());

        return new PageImpl<>(ratings, pageable, pagedRatings.getTotalElements());
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, UpdateProductDTO dto) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var product = repository.getReferenceById(id);
        product.update(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ProductDAO(product));
    }
}
