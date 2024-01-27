package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.ProductDAO;
import com.example.ecommerce.models.daos.RatingDAO;
import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.RatingDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.orms.Rating;
import com.example.ecommerce.repositories.CustomerRepository;
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
    private CustomerRepository customerRepository;



    @Transactional
    public ResponseEntity register(ProductDTO dto) {

        var product = new Product(dto);
        repository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDAO(product));
    }

    public ResponseEntity getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var product = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new ProductDAO(product));
    }

    public Page search(Pageable pageable, Long seller, String name) {

        Page<Product> productList = null;
        List<ProductDAO> pagedProducts;

        if (Objects.isNull(seller) && Objects.isNull(name)) productList = repository.findAll(pageable);
        if (Objects.isNull(seller) && !Objects.isNull(name)) productList = repository.findByName(pageable, name);
        if (!Objects.isNull(seller) && Objects.isNull(name)) productList = repository.findBySellerId(pageable, seller);
        if (!Objects.isNull(seller) && !Objects.isNull(name)) productList = repository.findBySellerIdAndName(pageable, seller, name);

        pagedProducts = productList.getContent().stream().map(ProductDAO::new).collect(Collectors.toList());

        return new PageImpl<>(pagedProducts, pageable, productList.getTotalElements());
    }

    @Transactional
    public ResponseEntity update(Long id, UpdateProductDTO dto) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var product = repository.getReferenceById(id);
        product.update(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ProductDAO(product));
    }

    @Transactional
    public ResponseEntity rating(Long productId, RatingDTO dto) {

        if (!repository.existsById(productId)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var product = repository.getReferenceById(productId);
        var customer = customerRepository.getReferenceById(dto.customerId());
        var rating = new Rating(dto, productId, customer.getName());

        product.rating(dto.rating());
        ratingRepository.save(rating);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RatingDAO(rating));
    }
}
