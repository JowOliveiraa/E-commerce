package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.SellerDAO;
import com.example.ecommerce.models.dtos.SellerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.entities.Seller;
import com.example.ecommerce.models.enums.Status;
import com.example.ecommerce.repositories.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SellerService {

    @Autowired
    private SellerRepository repository;


    @Transactional
    public ResponseEntity register(SellerDTO dto) {

        if (repository.existsByCpf(dto.cpf())) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado no sistema");
        }
        var seller = new Seller(dto);
        repository.save(seller);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SellerDAO(seller));

    }

    public Page<SellerDAO> search(Pageable pageable, String status, String search) {

        Page<Seller> sellersList = null;
        List<SellerDAO> pagedSellers;

        if (Objects.isNull(search)) sellersList = repository.findByStatus(pageable, status);
        if (!Objects.isNull(search)) sellersList = repository.search(pageable, status, search);

        pagedSellers = sellersList.getContent().stream().map(SellerDAO::new).collect(Collectors.toList());

        return new PageImpl<>(pagedSellers, pageable, sellersList.getTotalElements());

    }

    public ResponseEntity getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var seller = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new SellerDAO(seller));
    }

    @Transactional
    public ResponseEntity sellerStatus(Long id, String status) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var seller = repository.getReferenceById(id);

        if (Objects.equals(status, "active")) seller.setStatus(Status.ACTIVE);
        if (Objects.equals(status, "inactive")) seller.setStatus(Status.INACTIVE);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Status Alterado!");

    }


    public ResponseEntity update(Long id, UpdateDTO dto) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var seller = repository.getReferenceById(id);
        seller.update(dto);

        repository.save(seller);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SellerDAO(seller));
    }
}
