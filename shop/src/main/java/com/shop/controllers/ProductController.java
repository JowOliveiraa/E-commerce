package com.shop.controllers;

import com.shop.models.dtos.ProductDTO;
import com.shop.models.entities.Product;
import com.shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<Product>> createMultipleProducts(@RequestBody List<ProductDTO> dtos) {
        return service.createMultipleProducts(dtos);
    }

    @GetMapping("/listar-tudo")
    public Page<Product> listAllProducts(@PageableDefault(size = 10, page = 0)Pageable pageable) {
        return service.listAllProducts(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return service.updateProduct(id, dto);
    }

    @GetMapping
    public Page<Product> listAvailableProducts(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return service.listAvailableProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }
}
