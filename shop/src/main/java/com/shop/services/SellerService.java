package com.shop.services;

import com.shop.models.daos.SellerDAO;
import com.shop.models.dtos.SellerDTO;
import com.shop.models.entities.Seller;
import com.shop.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    public ResponseEntity<Object> createSeller(SellerDTO dto) {

        var seller = new Seller(dto);
        repository.save(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SellerDAO(seller));
    }

    public ResponseEntity<List<SellerDAO>> createMultipleSellers(List<SellerDTO> dto) {

        var sellers = dto.stream().map(Seller::new).collect(Collectors.toList());
        repository.saveAll(sellers);
        var sellersList = sellers.stream().map(SellerDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(sellersList);
    }
}
