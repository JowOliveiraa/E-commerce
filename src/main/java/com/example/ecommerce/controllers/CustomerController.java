package com.example.ecommerce.controllers;

import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody CustomerDTO dto) {
        return service.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }
}
