package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.AddressDAO;
import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.entities.Address;
import com.example.ecommerce.repositories.AddressRepository;
import com.example.ecommerce.repositories.UserRepository;
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
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private AuthenticationService authService;

    private static final String role = "CUSTOMER";

    @Transactional
    public ResponseEntity<Object> register(AddressDTO dto) {

        if (authService.notExists(dto.userId(), role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id do cliente invalido!");
        }

        if (repository.existsByUserId(dto.userId())) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente ja tem um endereço cadastrado!");
        }

        var address = new Address(dto);
        repository.save(address);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AddressDAO(address));
    }

    public ResponseEntity<Object> getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var address = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new AddressDAO(address));
    }

    public Page<AddressDAO> search(Pageable pageable, String search) {

        Page<Address> pagedAddresses = null;

        if (!Objects.isNull(search)) pagedAddresses = repository.search(pageable, search);
        else pagedAddresses = repository.findAll(pageable);

        List<AddressDAO> addresses = pagedAddresses.getContent().stream().map(AddressDAO::new).collect(Collectors.toList());

        return new PageImpl<>(addresses, pageable, pagedAddresses.getTotalElements());
    }
}
