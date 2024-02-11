package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.LoginDAO;
import com.example.ecommerce.models.daos.SellerDAO;
import com.example.ecommerce.models.dtos.LoginDTO;
import com.example.ecommerce.models.dtos.RegisterDTO;
import com.example.ecommerce.models.dtos.UpdateUserDTO;
import com.example.ecommerce.services.SellerService;
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
@RequestMapping("/seller")
@Tag(name = "Controller de vendedor", description = "Trata todas as requests relacionadas a vendedores.")
public class SellerController {

    @Autowired
    private SellerService service;

    @Operation(operationId = "registerSeller",
            summary = "Criar vendedor.",
            description = "Cria um vendedor, o campo cpf deve ser unico na aplicação.",
            responses = {
                @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  SellerDAO.class))),
                @ApiResponse(responseCode = "409", description = "CPF ja cadastrado.", content = @Content(mediaType = "String"))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDTO dto) {
        return service.register(dto);
    }

    @Operation(operationId = "loginSeller",
            summary = "Logar como vendedor.",
            description = "Autentica o vendedor e gera um token para ele. O token expira em 12 horas.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  LoginDAO.class))),
                    @ApiResponse(responseCode = "403", description = "Login invalido.")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO dto) {
        return service.login(dto);
    }

    @Operation(operationId = "getSellerById",
            summary = "buscar vendedor por id.",
            description = "Busca um vendedor pelo id dele.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  SellerDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(mediaType = "String"))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(operationId = "listAllSeller",
            summary = "buscar ou pesquisar vendedores.",
            description = "Busca todos os vendedores de forma paginada. O parametro search pesquisa por CPF ou nome, e status pesquisa por ACTIVE ou INACTIVE.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping
    public Page<SellerDAO> listAllSellers(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                          @RequestParam(required = false) String search,
                                          @RequestParam(required = false, defaultValue = "ACTIVE") String status
                                                  ) {
        return service.search(pageable, search, status);
    }

    @Operation(operationId = "statusSeller",
            summary = "alterar status do vendedor.",
            description = "Altera o status de um vendedor pelo id dele. O parametro active ou inactive deve ser passado na url, ao inativar um vendedor o token dele é invalidado. *Apenas vendedores autenticados.",
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
        return service.status(status, id);
    }

    @Operation(operationId = "updateSeller",
            summary = "editar vendedor.",
            description = "Edita o nome e email de um vendedor pelo id dele. *Apenas vendedores autenticados",
            security = @SecurityRequirement(name = "token"),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  SellerDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(mediaType = "String")),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        return service.update(id, dto);
    }
}
