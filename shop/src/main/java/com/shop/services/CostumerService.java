package com.shop.services;

import com.shop.models.daos.CostumerDAO;
import com.shop.models.dtos.CostumerDTO;
import com.shop.models.entities.Costumer;
import com.shop.repositories.CostumerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostumerService {

    @Autowired
    private CostumerRepository repository;

    @Transactional
    public ResponseEntity<Object> createCostumer(CostumerDTO dto) {

        var newCostumer = new Costumer(dto);
        repository.save(newCostumer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CostumerDAO(newCostumer));
    }

    public Page<CostumerDAO> listAllCostumers(Pageable pageable) {

        var costumers = repository.findAll(pageable);
        var costumersPage = costumers.getContent().stream().map(CostumerDAO::new).collect(Collectors.toList());
        return new PageImpl<>(costumersPage, pageable, costumers.getTotalElements());
    }

    @Transactional
    public ResponseEntity<List<CostumerDAO>> createMultipleCostumers(List<CostumerDTO> dtos) {

        var costumers = dtos.stream().map(Costumer::new).collect(Collectors.toList());
        repository.saveAll(costumers);
        var createdCostumers = costumers.stream().map(CostumerDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCostumers);
    }

    @Transactional
    public ResponseEntity<Object> updateCostumer(Long id, CostumerDTO dto) {

        if (repository.existsById(id)) {
            var costumer = repository.getReferenceById(id);
            costumer.update(dto);
            var updatedCostumer = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CostumerDAO(updatedCostumer));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    public ResponseEntity<Object> getById(Long id) {

        if (repository.existsById(id)) {
            var costumer = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CostumerDAO(costumer));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

    }

    @Transactional
    public ResponseEntity<String> deleteCostumer(Long id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    public ResponseEntity<Object> validateCpf(String cpf) {

        if (repository.existsByCpf(cpf)) {
            var costumerCpf = new CostumerDAO((Costumer) repository.getReferenceByCpf(cpf));
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado no sistema para "+ costumerCpf.name());
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("CPF disponível.");
        }
    }

    public Page<CostumerDAO> search(Pageable pageable, String search, String status) {

        Page<Costumer> costumers;
        List<CostumerDAO> pagedCostumers;

        if (status == null) {
            costumers = repository.findAll(pageable);
            pagedCostumers = costumers.stream().map(CostumerDAO::new).collect(Collectors.toList());

        } else {
            costumers = repository.search(pageable, search, status);
            pagedCostumers = costumers.getContent().stream().map(CostumerDAO::new).collect(Collectors.toList());
        }

        return new PageImpl<>(pagedCostumers, pageable, costumers.getTotalElements());
    }
}
