package com.shop.controllers;

import com.shop.models.daos.CustomerDAO;
import com.shop.models.dtos.CustomerDTO;
import com.shop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO dto) {
        return service.createCustomer(dto);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<CustomerDAO>> createMultipleCustomers(@RequestBody List<CustomerDTO> dtos) {
        return service.createMultipleCustomers(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO dto) {
        return service.updateCostumer(id, dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/status/{id}")
    public ResponseEntity<String> inactiveCustomer(@PathVariable Long id) {
        return service.inactiveCustomer(id);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> activeCustomer(@PathVariable Long id) {
        return service.activeCustomer(id);
    }

    @GetMapping("/validate-cpf")
    public ResponseEntity<Object> validateCpf(@RequestParam String cpf) {
        return service.validateCpf(cpf);
    }

    @GetMapping
    public Page<CustomerDAO> listCustomers(@PageableDefault(size = 10, page = 0, sort = {"name"}) Pageable pageable,
                                           @RequestParam(required = false, defaultValue = "") String search,
                                           @RequestParam(required = false) String status) {
        return service.search(pageable, search, status);
    }

}
