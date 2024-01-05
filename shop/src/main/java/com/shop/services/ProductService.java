package com.shop.services;

import com.shop.models.daos.ProductDAO;
import com.shop.models.dtos.ProductDTO;
import com.shop.models.entities.Product;
import com.shop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional
    public ResponseEntity<Object> createProduct(ProductDTO dto) {

        var product = new Product(dto);
        repository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDAO(product));
    }

    @Transactional
    public ResponseEntity<List<ProductDAO>> createMultipleProducts(List<ProductDTO> dtos) {

        var products = dtos.stream().map(Product::new).collect(Collectors.toList());
        repository.saveAll(products);
        var createdProducts = products.stream().map(ProductDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);
    }

    public Page<ProductDAO> search(Pageable pageable, Boolean onlyAvailable, String name, String category) {

        Page<Product> products = null;
        List<ProductDAO> pagedProducts = null;
        
        if (onlyAvailable) {

            if (Objects.isNull(name) && Objects.isNull(category)) products = repository.findByAvailable(pageable);
            if (!Objects.isNull(name) && Objects.isNull(category)) products = repository.findByAvailableNames(pageable, name);
            if (!Objects.isNull(name) && !Objects.isNull(category)) products = repository.findByAvailableNamesAndCategories(pageable, name, category);
            if (Objects.isNull(name) && !Objects.isNull(category)) products = repository.findByAvailableCategory(pageable, category);

        } else {

            if (Objects.isNull(name) && Objects.isNull(category)) products = repository.findAll(pageable);
            if (!Objects.isNull(name) && Objects.isNull(category)) products = repository.findByNotAvailableNames(pageable, name);
            if (!Objects.isNull(name) && !Objects.isNull(category)) products = repository.findByNotAvailableNamesAndCategories(pageable, name, category);
            if (Objects.isNull(name) && !Objects.isNull(category)) products = repository.findByNotAvailableCategory(pageable, category);

        }
        pagedProducts = products.getContent().stream().map(ProductDAO::new).collect(Collectors.toList());
        return new PageImpl<>(pagedProducts, pageable, products.getTotalElements());
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

    public void removeQuantity(Long id) {

        var product = repository.getReferenceById(id);
        product.removeQuantity();

    }
}
