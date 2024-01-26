package com.example.ecommerce.controllers;

import com.example.ecommerce.models.dtos.AdminDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AdminDTO dto) {
        return service.register(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page listAllAdmins(@PageableDefault(size = 10, page = 0)Pageable pageable,
                              @RequestParam(required = false, defaultValue = "ACTIVE") String status,
                              @RequestParam(required = false) String search
                              ) {
        return service.search(pageable, status, search);
    }

    @PutMapping("/{status}/{id}")
    public ResponseEntity adminStatus(@PathVariable Long id, @PathVariable String status) {
        return service.adminStatus(id, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UpdateDTO dto) {
        return service.update(id, dto);
    }
}
