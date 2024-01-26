package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
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
    public ResponseEntity register(@RequestBody CustomerDTO dto) {
        return service.register(dto);
    }

    @GetMapping
    public Page<CustomerDAO> listAllCustomers(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                              @RequestParam(defaultValue = "ACTIVE", required = false) String status,
                                              @RequestParam(required = false) String search
                                              ) {
        return service.listAllCustomer(pageable, status, search);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return service.getById(id);

    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity customerStatus(@PathVariable Long id,@PathVariable String status) {
        return service.customerStatus(id, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateDTO dto) {
        return service.update(id, dto);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity getAddressById(@PathVariable Long id) {
        return service.getAddressById(id);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity updateAddress(@PathVariable Long id, @RequestBody AddressDTO dto) {
        return service.updateAddress(id, dto);
    }
}
