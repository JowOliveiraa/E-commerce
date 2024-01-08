package com.shop.services;

import com.shop.models.daos.CustomerDAO;
import com.shop.models.dtos.CustomerDTO;
import com.shop.models.entities.Customer;
import com.shop.repositories.CustomerRepository;
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
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Transactional
    public ResponseEntity<Object> createCustomer(CustomerDTO dto) {

        var newCustomer = new Customer(dto);
        repository.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerDAO(newCustomer));
    }

    @Transactional
    public ResponseEntity<List<CustomerDAO>> createMultipleCustomers(List<CustomerDTO> dtos) {

        var customers = dtos.stream().map(Customer::new).collect(Collectors.toList());
        repository.saveAll(customers);
        var createdCustomers = customers.stream().map(CustomerDAO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomers);
    }

    @Transactional
    public ResponseEntity<Object> updateCostumer(Long id, CustomerDTO dto) {

        if (repository.existsById(id)) {

            var customer = repository.getReferenceById(id);
            customer.update(dto);
            var updatedCustomer = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomerDAO(updatedCustomer));

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }
    }

    public ResponseEntity<Object> getById(Long id) {

        if (repository.existsById(id)) {

            var customer = repository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new CustomerDAO(customer));

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

    }

    @Transactional
    public ResponseEntity<String> inactiveCustomer(Long id) {

            var customer = repository.getReferenceById(id);
            customer.inactive();
            return ResponseEntity.status(HttpStatus.OK).body("Cliente inativado!");
    }

    public ResponseEntity<Object> validateCpf(String cpf) {

        if (repository.existsByCpf(cpf)) {

            var customerCpf = new CustomerDAO((Customer) repository.getReferenceByCpf(cpf));
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado no sistema para "+ customerCpf.name());

        } else {

            return ResponseEntity.status(HttpStatus.ACCEPTED).body("CPF disponível.");
        }
    }

    public Page<CustomerDAO> search(Pageable pageable, String search, String status) {

        Page<Customer> customers;
        List<CustomerDAO> pagedCustomers;

        if (status == null) {

            customers = repository.findAll(pageable);
            pagedCustomers = customers.stream().map(CustomerDAO::new).collect(Collectors.toList());

        } else {

            customers = repository.search(pageable, search, status);
            pagedCustomers = customers.getContent().stream().map(CustomerDAO::new).collect(Collectors.toList());
        }

        return new PageImpl<>(pagedCustomers, pageable, customers.getTotalElements());
    }

    @Transactional
    public ResponseEntity<String> activeCustomer(Long id) {

        var customer = repository.getReferenceById(id);
        customer.active();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Cliente ativado!");
    }

    public void addPurchase(Long id) {

        var customer = repository.getReferenceById(id);
        customer.addPurchase();
    }
}
