package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.SellerDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.services.AuthenticationService;
import com.example.ecommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService service;

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
    public Page<SellerDAO> listAllSellers(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false, defaultValue = "ACTIVE") String status
                                                  ) {
        return service.search(pageable, search, status);
    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity<Object> status(@PathVariable String status, @PathVariable Long id) {
        return service.status(status, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateDTO dto) {
        return service.update(id, dto);
    }
}
