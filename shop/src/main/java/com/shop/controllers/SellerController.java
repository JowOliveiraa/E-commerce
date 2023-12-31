package com.shop.controllers;

import com.shop.models.daos.SellerDAO;
import com.shop.models.dtos.SellerDTO;
import com.shop.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendedores")
public class SellerController {

    @Autowired
    private SellerService service;


    @PostMapping
    public ResponseEntity<Object> createSeller(@RequestBody SellerDTO dto) {
        return service.createSeller(dto);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<SellerDAO>> createMultipleSellers(@RequestBody List<SellerDTO> dto) {
        return service.createMultipleSellers(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSellerById(@PathVariable Long id) {
        return service.getSellerById(id);
    }

    @GetMapping
    public Page<SellerDAO> listAllSeller(@PageableDefault(size = 10, page = 0, sort = {"name"})Pageable pageable,
                                         @RequestParam(required = false, defaultValue = "") String search,
                                         @RequestParam(required = false) String status) {
        return service.search(pageable, search, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSeller(@PathVariable Long id, @RequestBody SellerDTO dto) {
        return service.updateSeller(id, dto);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> activeSeller(@PathVariable Long id) {
        return service.activeSeller(id);
    }

    @DeleteMapping("status/{id}")
    public ResponseEntity<String> inactiveSeller(@PathVariable Long id) {
        return service.inactiveSeller(id);
    }
}
