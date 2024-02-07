package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.ProductDAO;
import com.example.ecommerce.models.daos.RatingDAO;
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
    public ResponseEntity<Object> register(@RequestBody ProductDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/rating")
    public ResponseEntity<Object> rating(@RequestBody RatingDTO dto) {
        return service.rating(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<ProductDAO> listAllProducts(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                            @RequestParam(required = false)String name,
                                            @RequestParam(required = false)String category,
                                            @RequestParam(required = false)Long seller
    ) {
        return service.search(pageable, name, category, seller);
    }

    @GetMapping("/rating/{product}")
    public Page<RatingDAO> getRatings(@PageableDefault(size = 10, page = 0)Pageable pageable, @PathVariable Long product) {
        return service.getRatings(pageable, product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return service.update(id,dto);
    }

}
