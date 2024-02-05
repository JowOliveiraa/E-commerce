package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateUserDTO;
import com.example.ecommerce.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {


    @Autowired
    private CustomerService service;


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<CustomerDAO> listAllCustomer(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                             @RequestParam(required = false, defaultValue = "ACTIVE")String status,
                                             @RequestParam(required = false)String search
    ) {
        return service.search(pageable, status, search);
    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity<Object> status(@PathVariable String status, @PathVariable Long id) {
        return service.status(id, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        return service.update(id, dto);
    }

}
