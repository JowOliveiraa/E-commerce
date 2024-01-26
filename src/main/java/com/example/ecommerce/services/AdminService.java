package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.AdminDAO;
import com.example.ecommerce.models.dtos.AdminDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.entities.Admin;
import com.example.ecommerce.models.enums.Status;
import com.example.ecommerce.repositories.AdminRepository;
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
public class AdminService {

    @Autowired
    private AdminRepository repository;

    @Transactional
    public ResponseEntity register(AdminDTO dto) {

        if (repository.existsByCpf(dto.cpf())) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado no sistema!");
        }
        var admin = new Admin(dto);
        repository.save(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(new AdminDAO(admin));
    }

    public ResponseEntity getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var admin = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new AdminDAO(admin));
    }

    public Page search(Pageable pageable, String status, String search) {

        Page<Admin> adminsList = null;
        List<AdminDAO> pagedAdmins;

        if (Objects.isNull(search)) adminsList = repository.findByStatus(pageable, status);
        if (!Objects.isNull(search)) adminsList = repository.search(pageable, status, search);

        pagedAdmins = adminsList.getContent().stream().map(AdminDAO::new).collect(Collectors.toList());

        return new PageImpl<>(pagedAdmins, pageable, adminsList.getTotalElements());
    }

    @Transactional
    public ResponseEntity adminStatus(Long id, String status) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var admin = repository.getReferenceById(id);

        if (Objects.equals(status, "active")) admin.setStatus(Status.ACTIVE);
        if (Objects.equals(status, "inactive")) admin.setStatus(Status.INACTIVE);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Status alterado!");
    }

    @Transactional
    public ResponseEntity update(Long id, UpdateDTO dto) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var admin = repository.getReferenceById(id);

        admin.update(dto);
        repository.saveAndFlush(admin);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AdminDAO(admin));
    }
}
