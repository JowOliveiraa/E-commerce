package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.CustomerDAO;
import com.example.ecommerce.models.daos.LoginDAO;
import com.example.ecommerce.models.daos.SellerDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateUserDTO;
import com.example.ecommerce.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@Tag(name = "Controller de cliente", description = "Trata todas as requests relacionadas a clientes.")
public class CustomerController {


    @Autowired
    private CustomerService service;


    @Operation(operationId = "registerCustomer",
            summary = "Criar cliente.",
            description = "Cria um cliente, o campo cpf deve ser unico na aplicação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  CustomerDAO.class))),
                    @ApiResponse(responseCode = "409", description = "CPF ja cadastrado.", content = @Content(mediaType = "String"))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }

    @Operation(operationId = "loginCustomer",
            summary = "Logar como cliente.",
            description = "Autentica o cliente e gera um token para ele. O token expira em 12 horas.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  LoginDAO.class))),
                    @ApiResponse(responseCode = "403", description = "Login invalido.")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @Operation(operationId = "getCustomerById",
            summary = "buscar cliente por id.",
            security = @SecurityRequirement(name = "token"),
            description = "Busca um cliente pelo id dele. *Apenas clientes autenticados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  CustomerDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(mediaType = "String"))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(operationId = "listAllCustomers",
            summary = "buscar ou pesquisar clientes.",
            security = @SecurityRequirement(name = "token"),
            description = "Busca todos os clientes de forma paginada. O parametro search pesquisa por CPF ou nome, e status pesquisa por ACTIVE ou INACTIVE. *Apenas Admins autenticados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.")
            }
    )
    @GetMapping
    public Page<CustomerDAO> listAllCustomer(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                             @RequestParam(required = false, defaultValue = "ACTIVE")String status,
                                             @RequestParam(required = false)String search
    ) {
        return service.search(pageable, status, search);
    }

    @Operation(operationId = "statusCustomer",
            summary = "alterar status do cliente.",
            description = "Altera o status de um cliente pelo id dele. O parametro active ou inactive deve ser passado na url, ao inativar um cliente o token dele é invalidado. *Apenas clientes autenticados.",
            security = @SecurityRequirement(name = "token"),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito."),
                    @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(mediaType = "String", schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "O status passado é invalido.", content = @Content(mediaType = "String", schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.")
            }
    )
    @PutMapping("/{status}/{id}")
    public ResponseEntity<Object> status(@PathVariable String status, @PathVariable Long id) {
        return service.status(id, status);
    }

    @Operation(operationId = "updateCustomer",
            summary = "editar cliente.",
            description = "Edita o nome e email de um cliente pelo id dele. *Apenas clientes autenticados",
            security = @SecurityRequirement(name = "token"),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  CustomerDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(mediaType = "String")),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        return service.update(id, dto);
    }

}
