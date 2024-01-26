package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.SellerDAO;
import com.example.ecommerce.models.dtos.SellerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody SellerDTO dto) {
        return service.register(dto);
    }

    @GetMapping
    public Page<SellerDAO> listAllSellers(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                          @RequestParam(required = false, defaultValue = "ACTIVE") String status,
                                          @RequestParam(required = false) String search
                                          ) {
        return service.search(pageable, status, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity sellerStatus(@PathVariable Long id, @PathVariable String status) {
        return service.sellerStatus(id, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateDTO dto) {
        return service.update(id, dto);
    }

}
