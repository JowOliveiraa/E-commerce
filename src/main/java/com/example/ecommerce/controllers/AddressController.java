package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.AddressDAO;
import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.dtos.UpdateAddressDTO;
import com.example.ecommerce.services.AddressService;
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
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/address")
@Tag(name = "Controller de endereço", description = "Trata todas as requests relacionadas a endereços.")
@SecurityRequirement(name = "token")
public class AddressController {

    @Autowired
    private AddressService service;

    @Operation(operationId = "registerAddress",
            summary = "Criar endereço.",
            description = "Cria um novo endereço. *Apenas clientes autenticados podem criar um endereço, cada cliente pode ter apenas um endereço.",
            responses = {
                @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json",schema = @Schema(implementation = AddressDAO.class))),
                @ApiResponse(responseCode = "404", description = "Id do cliente invalido.", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse(responseCode = "409", description = "Cliente ja tem um endereço cadastrado.", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse(responseCode = "403", description = "Não autorizado.")
    })
    @PostMapping
    public ResponseEntity<Object> register(@RequestBody AddressDTO dto) {
        return service.register(dto);
    }

    @Operation(operationId = "getAddressByUserId",
            summary = "Buscar endereço.",
            description = "Busca um endereço pelo id do cliente. *Apenas clientes autenticados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json",schema = @Schema(implementation = AddressDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id do cliente invalido.", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.")
            })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(operationId = "listAllAddresses",
            summary = "Buscar ou pesquisar por endereços.",
            description = "Busca todos os endereços de forma paginada, também permite pesquisar por Rua ou CEP. *Apenas clientes autenticados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok."),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.", content = @Content(mediaType = "null"))
            })
    @GetMapping
    public Page<AddressDAO> listAllAddresses(@PageableDefault(size = 10, page = 0, sort = {"id"})Pageable pageable,
                                             @RequestParam(required = false)String search) {
        return service.search(pageable, search);
    }

    @Operation(operationId = "updateAddressById",
            summary = "Atualizar endereço.",
            description = "Atualiza um endereço pelo id dele. *Apenas clientes autenticados.",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json",schema = @Schema(implementation = AddressDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id do endereço invalido.", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Não autorizado.")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateAddressDTO dto) {
        return service.update(id, dto);
    }
}
