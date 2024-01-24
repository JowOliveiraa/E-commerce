package com.example.ecommerce.controllers;

import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controller de customer", description = "centraliza todas as requests vinculadas ao clinte.")
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(summary = "Criar um cliente", method = "POST",
    responses = {
            @ApiResponse(responseCode = "201", description = "CREATED: Devolve as informações basicas do cliente que acabou de ser criado."),
            @ApiResponse(responseCode = "409", description = "CONFLICT: CPF ja existente no banco."),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN: Informações invalidas na requisição.")
    })
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody CustomerDTO dto) {
        return service.register(dto);
    }

    @Operation(summary = "fazer login como cliente",
    method = "POST", responses = {
            @ApiResponse(responseCode = "202", description = "ACCEPTED: Devolve o id, a role e o token do usuario."),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN: Informações invalidas na requisição.")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

}
