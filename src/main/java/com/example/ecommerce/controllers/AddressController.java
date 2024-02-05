package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.AddressDAO;
import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.dtos.UpdateAddressDTO;
import com.example.ecommerce.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody AddressDTO dto) {
        return service.register(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<AddressDAO> listAllAddresses(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                             @RequestParam(required = false)String search) {
        return service.search(pageable, search);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateAddressDTO dto) {
        return service.update(id, dto);
    }
}
