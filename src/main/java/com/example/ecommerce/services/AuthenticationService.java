package com.example.ecommerce.services;

import com.example.ecommerce.models.daos.LoginDAO;
import com.example.ecommerce.models.daos.RegisterDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;
import com.example.ecommerce.repositories.UserRepository;
import com.example.ecommerce.security.TokenService;
import jakarta.transaction.Transactional;
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

import java.util.Objects;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authManager;


    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository.findByCpf(cpf);
    }

    @Transactional
    public ResponseEntity<Object> register(RegisterDTO dto, Role role) {

        if (this.repository.findByCpf(dto.cpf()) != null) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF ja cadastrado!");
        }

        var user = new User(dto, role);

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        user.setPassword(encryptedPassword);

        repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterDAO(user));
    }


    public ResponseEntity<Object> login(LoginDTO dto) {

        authManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.cpf(), dto.password());
        var auth = this.authManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        User user = (User) repository.findByCpf(dto.cpf());

        if (user.getStatus() == Status.INACTIVE) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario inativo!");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new LoginDAO(user, token));
    }

    public boolean notExists(Long id, String role) {

        return Objects.isNull(repository.validateRole(id, role));
    }
}
