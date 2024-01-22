package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.daos.LoginDAO;
import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.entities.Customer;
import com.example.ecommerce.repositories.CustomerRepository;
import com.example.ecommerce.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TokenService service;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository.findByCpf(cpf);
    }

    public ResponseEntity register(CustomerDTO dto) {

        try {
            if (this.repository.existsByCpf(dto.cpf())) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cpf ja cadastrado para um cliente!");
            }

            var customer = new Customer(dto);

            String password = new BCryptPasswordEncoder().encode(dto.password());
            customer.setPassword(password);

            repository.save(customer);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerDAO(customer));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity login(LoginDTO dto) {

        authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.cpf(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = service.generateToken((Customer) auth.getPrincipal());

        var customer = repository.getReferenceByCpf(dto.cpf());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginDAO(customer, token));
    }
}
