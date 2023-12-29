package com.shop.controllers;

import com.shop.models.daos.SellerDAO;
import com.shop.models.dtos.SellerDTO;
import com.shop.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
