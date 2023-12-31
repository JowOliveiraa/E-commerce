package com.shop.controllers;

import com.shop.models.daos.CostumerDAO;
import com.shop.models.dtos.CostumerDTO;
import com.shop.services.CostumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class CostumerController {

    @Autowired
    private CostumerService service;

    @PostMapping
    public ResponseEntity<Object> createCostumer(@RequestBody CostumerDTO dto) {
        return service.createCostumer(dto);
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

    @DeleteMapping("/status/{id}")
    public ResponseEntity<String> inactiveCostumer(@PathVariable Long id) {
        return service.inactiveCostumer(id);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> activeCostumer(@PathVariable Long id) {
        return service.activeCostumer(id);
    }

    @GetMapping("/validate-cpf")
    public ResponseEntity<Object> validateCpf(@RequestParam String cpf) {
        return service.validateCpf(cpf);
    }

    @GetMapping
    public Page<CostumerDAO> listCostumers(@PageableDefault(size = 10, page = 0, sort = {"name"}) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String search,
                                 @RequestParam(required = false) String status) {
        return service.search(pageable, search, status);
    }

}
