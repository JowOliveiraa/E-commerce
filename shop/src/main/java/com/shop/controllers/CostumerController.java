package com.shop.controllers;

import com.shop.models.daos.CostumerDAO;
import com.shop.models.dtos.CostumerDTO;
import com.shop.models.entities.Costumer;
import com.shop.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/clientes")
public class CostumerController {

    @Autowired
    private CostumerService service;

    @PostMapping
    public ResponseEntity<Object> createCostumer(@RequestBody CostumerDTO dto) {
        return service.createCostumer(dto);
    }

    @GetMapping
    public Page<CostumerDAO> listAllCostumers(@PageableDefault(page = 0, size = 10, sort = {"name"}) Pageable pageable) {
        return service.listAllCostumers(pageable);
    }

    @PostMapping("/varios")
    public ResponseEntity<List<CostumerDAO>> createMultipleCostumers(@RequestBody List<CostumerDTO> dtos) {
        return service.createMultipleCostumers(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCostumer(@PathVariable Long id, @RequestBody CostumerDTO dto) {
        return service.updateCostumer(id, dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCostumer(@PathVariable Long id) {
        return service.deleteCostumer(id);
    }

    @GetMapping("/validate-cpf")
    public ResponseEntity<Object> validateCpf(@RequestParam String cpf) {
        return service.validateCpf(cpf);
    }
}
