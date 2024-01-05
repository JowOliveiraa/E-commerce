package com.shop.services;

import com.shop.models.daos.SellerDAO;
import com.shop.models.dtos.SellerDTO;
import com.shop.models.entities.Seller;
import com.shop.repositories.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;

    @Transactional
    public ResponseEntity<Object> createSeller(SellerDTO dto) {

        var seller = new Seller(dto);
        repository.save(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SellerDAO(seller));
    }

    @Transactional
    public ResponseEntity<List<SellerDAO>> createMultipleSellers(List<SellerDTO> dto) {

        var sellers = dto.stream().map(Seller::new).collect(Collectors.toList());
        repository.saveAll(sellers);
        var sellersList = sellers.stream().map(SellerDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(sellersList);
    }

    public ResponseEntity<Object> getSellerById(Long id) {

        if (repository.existsById(id)) {

            var seller = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new SellerDAO(seller));

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendedor não encontrado!");
        }
    }

    @Transactional
    public ResponseEntity<Object> updateSeller(Long id, SellerDTO dto) {

        var seller = repository.getReferenceById(id);
        seller.update(dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SellerDAO(seller));
    }

    @Transactional
    public ResponseEntity<String> inactiveSeller(Long id) {

        var seller = repository.getReferenceById(id);
        seller.inactive();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Vendedor inativado!");
    }

    @Transactional
    public ResponseEntity<String> activeSeller(Long id) {

        var seller = repository.getReferenceById(id);
        seller.active();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Vendedor Ativo!");
    }

    public Page<SellerDAO> search(Pageable pageable, String search, String status) {

        Page<Seller> sellers = null;
        List<SellerDAO> pagedSellers = null;

        if (status == null) {

            sellers = repository.findAll(pageable);
            pagedSellers = sellers.stream().map(SellerDAO::new).collect(Collectors.toList());

        } else {

            sellers = repository.search(pageable, search, status);
            pagedSellers = sellers.getContent().stream().map(SellerDAO::new).collect(Collectors.toList());
        }

        return new PageImpl<>(pagedSellers, pageable, sellers.getTotalElements());
    }

    public void addSale(Long id) {

        var seller = repository.getReferenceById(id);
        seller.addSale();
    }
}
