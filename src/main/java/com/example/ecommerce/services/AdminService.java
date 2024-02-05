package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.AdminDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateUserDTO;
import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;
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
public class AdminService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationService authService;

    private static final String role = "ADMIN";

    public ResponseEntity<Object> register(RegisterDTO dto) {

        return authService.register(dto, Role.ADMIN);
    }

    public ResponseEntity<Object> login(LoginDTO dto) {

        return authService.login(dto);
    }

    public ResponseEntity<Object> getById(Long id) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var admin = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new AdminDAO(admin));
    }

    public Page<AdminDAO> search(Pageable pageable, String search, String status) {

        Page<User> pagedAdmins = null;

         if (!Objects.isNull(search)) pagedAdmins = repository.findByNameOrCpf(pageable, role, status, search);
         else pagedAdmins = repository.find(pageable, role, status);

        List<AdminDAO> admins = pagedAdmins.getContent().stream().map(AdminDAO::new).collect(Collectors.toList());

        return new PageImpl<>(admins, pageable, pagedAdmins.getTotalElements());
    }

    @Transactional
    public ResponseEntity<Object> status(String status, Long id) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var admin = repository.getReferenceById(id);

        if (Objects.equals(status, "active")) admin.setStatus(Status.ACTIVE);
        else if (Objects.equals(status, "inactive")) admin.setStatus(Status.INACTIVE);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status invalido!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, UpdateUserDTO dto) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var admin = repository.getReferenceById(id);
        admin.update(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AdminDAO(admin));
    }
}
