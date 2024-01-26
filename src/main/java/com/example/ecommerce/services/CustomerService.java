package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.AddressDAO;
import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.entities.Customer;
import com.example.ecommerce.models.enums.Status;
import com.example.ecommerce.repositories.AddressRepository;
import com.example.ecommerce.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public ResponseEntity register(CustomerDTO dto) {

        if (repository.existsByCpf(dto.cpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado no sistema!");
        }

        var customer = new Customer(dto);
        repository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerDAO(customer));
    }

    public Page<CustomerDAO> listAllCustomer(Pageable pageable, String status, String search) {

        Page<Customer> customersList = null;
        List<CustomerDAO> pagedCustomers = null;

        if (Objects.isNull(search)) customersList = repository.findByStatus(status, pageable);
        if (!Objects.isNull(search)) customersList = repository.search(status, search, pageable);

        pagedCustomers = customersList.getContent().stream().map(CustomerDAO::new).collect(Collectors.toList());

        return new PageImpl<>(pagedCustomers, pageable, customersList.getTotalElements());
    }

    @Transactional
    public ResponseEntity customerStatus(Long id, String status) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id invalido!");
        }
        var customer = repository.getReferenceById(id);
        
        if (Objects.equals(status, "inactive")) customer.setStatus(Status.INACTIVE);
        if (Objects.equals(status, "active")) customer.setStatus(Status.ACTIVE);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Status alterado!");
    }

    public ResponseEntity getById(Long id) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var customer = repository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CustomerDAO(customer));
    }

    @Transactional
    public ResponseEntity update(Long id, UpdateDTO dto) {

        if (!repository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var customer = repository.getReferenceById(id);
        customer.update(dto);
        repository.saveAndFlush(customer);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CustomerDAO(customer));
    }


    @Transactional
    public ResponseEntity updateAddress(Long id, AddressDTO dto) {

        if (!addressRepository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var address = addressRepository.getReferenceById(id);
        address.update(dto);

        addressRepository.saveAndFlush(address);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AddressDAO(address));
    }

    public ResponseEntity getAddressById(Long id) {

        if (!addressRepository.existsById(id)) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id invalido!");
        }
        var address = addressRepository.getReferenceById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new AddressDAO(address));
    }
}
