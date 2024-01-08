package com.shop.controllers;

import com.shop.models.daos.SaleDAO;
import com.shop.models.dtos.SaleDTO;
import com.shop.models.orms.Sale;
import com.shop.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class SaleController {

    @Autowired
    private SaleService service;

    @PostMapping
    public ResponseEntity<SaleDAO> createSale(@RequestBody SaleDTO dto) {
        return service.createSale(dto);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<SaleDAO>> createMultipleSales(@RequestBody List<SaleDTO> dtos) {
        return service.createMultipleSales(dtos);
    }

    @GetMapping
    public Page<SaleDAO> listALLSales(@PageableDefault(size = 10,page = 0)Pageable pageable,
                                      @RequestParam(required = false) Long customerId,
                                      @RequestParam(required = false) Long sellerId) {
        return service.search(pageable, customerId, sellerId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSaleById(@PathVariable Long id) {
        return service.getSaleById(id);
    }
}
