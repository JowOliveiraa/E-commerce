package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService service;

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody SaleDTO dto) {
        return service.register(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<SaleDAO> listAllSales(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                      @RequestParam(required = false)Long customer,
                                      @RequestParam(required = false)Long seller,
                                      @RequestParam(required = false)Long product
                                      ) {
        return service.search(pageable, customer, seller, product);
    }
}
