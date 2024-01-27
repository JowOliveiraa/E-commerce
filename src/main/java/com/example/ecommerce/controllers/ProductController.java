package com.example.ecommerce.controllers;

import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.RatingDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ProductDTO dto) {
        return service.register(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page listAllProducts(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                @RequestParam(required = false) Long seller,
                                @RequestParam(required = false) String name
                                ) {
        return service.search(pageable, seller, name);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return service.update(id, dto);
    }

    @PostMapping("/{productId}")
    public ResponseEntity rating(@PathVariable Long productId, @RequestBody RatingDTO dto) {
        return service.rating(productId, dto);
    }

}
