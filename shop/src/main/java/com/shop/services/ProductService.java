package com.shop.services;

import com.shop.models.daos.ProductDAO;
import com.shop.models.dtos.ProductDTO;
import com.shop.models.entities.Costumer;
import com.shop.models.entities.Product;
import com.shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public ResponseEntity<Object> createProduct(ProductDTO dto) {

        var product = new Product(dto);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @Transactional
    public ResponseEntity<List<Product>> createMultipleProducts(List<ProductDTO> dtos) {

        var products = dtos.stream().map(Product::new).collect(Collectors.toList());
        repository.saveAll(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(products);
    }

    public Page<Product> listAllProducts(Pageable pageable) {

        return repository.findAll(pageable);
    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Long id) {

        if (repository.existsById(id)) {
            var product = new ProductDAO(repository.getReferenceById(id));
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(product.name() +"Deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
    }

    @Transactional
    public ResponseEntity<Object> updateProduct(Long id, ProductDTO dto) {

        if (repository.existsById(id)) {
            var product = repository.getReferenceById(id);
            product.update(dto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ProductDAO(product));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
    }

    public Page<Product> listAvailableProducts(Pageable pageable) {

        return repository.findByAvailable(pageable);
    }

    public ResponseEntity<Object> getProductById(Long id) {

        if (repository.existsById(id)) {
            var product = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDAO(product));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado ou esgotado.");
        }
    }
}
