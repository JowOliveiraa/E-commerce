package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
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
public class CustomerService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationService authService;

    private static final String role = "CUSTOMER";

    public ResponseEntity<Object> register(RegisterDTO dto) {

        return authService.register(dto, Role.CUSTOMER);
    }

    public ResponseEntity<Object> login(LoginDTO dto) {

        return authService.login(dto);
    }

    public ResponseEntity<Object> getById(Long id) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var customer = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerDAO(customer));
    }

    public Page<CustomerDAO> search(Pageable pageable, String status, String search) {

        Page<User> pagedCustomers = null;

        if (!Objects.isNull(search)) pagedCustomers = repository.findByNameOrCpf(pageable, role, status, search);
        else pagedCustomers = repository.find(pageable, role, status);

        List<CustomerDAO> customers = pagedCustomers.getContent().stream().map(CustomerDAO::new).collect(Collectors.toList());

        return new PageImpl<>(customers, pageable, pagedCustomers.getTotalElements());
    }

    @Transactional
    public ResponseEntity<Object> status(Long id, String status) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var customer = repository.getReferenceById(id);

        if (Objects.equals(status, "active")) customer.setStatus(Status.ACTIVE);
        else if (Objects.equals(status, "inactive")) customer.setStatus(Status.INACTIVE);
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status invalido!");

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Transactional
    public ResponseEntity<Object> update(Long id, UpdateDTO dto) {

        if (authService.notExists(id, role)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }

        var customer = repository.getReferenceById(id);
        customer.update(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomerDAO(customer));
    }
}
