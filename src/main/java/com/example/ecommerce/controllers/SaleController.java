package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.SaleDAO;
import com.example.ecommerce.models.dtos.SaleDTO;
import com.example.ecommerce.services.SaleService;
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
@RequestMapping("/sale")
@Tag(name = "Controller de venda", description = "Trata todas as requests relacionadas a vendas.")
@SecurityRequirement(name = "token")
public class SaleController {

    @Autowired
    private SaleService service;


    @Operation(operationId = "registerSale",
           summary = "Criar venda.",
           description = "*Apenas clientes autenticados. <br><br>" +
                         "- Verifica os Ids dos produtos e do cliente. <br><br>" +
                         "- Verifica na base de dados se tem a quantidade suficiente dos produtos pedidos. <br><br>" +
                         "- Para cada produto do pedido remove a quantidade dele na base de dados conforme o pedido. <br><br>" +
                         "- Para cada produto do pedido adiciona uma venda ao vendedor que registrou aquele produto. <br><br>" +
                         "- Para cada produto do pedido aumenta a quantidade do numero de vendas dele conforme a quantidade do pedido. <br><br>" +
                         "- Adiciona uma venda para o cliente. <br><br>" +
                         "- Salva a venda",
           responses = {
                @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDAO.class))),
                @ApiResponse(responseCode = "404", description = "Id do cliente ou de algum dos produtos invalido.", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse(responseCode = "409", description = "Quantidade de um dos produtos insuficiente.", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse(responseCode = "403", description = "Não autorizado.")
           }
    )
    @PostMapping
    public ResponseEntity<Object> register(@RequestBody SaleDTO dto) {
        return service.register(dto);
    }

    @Operation(operationId = "getById",
            summary = "Buscar venda por Id.",
            description = "Busca uma venda pelo id dela. *Apenas clientes autenticados.",
            responses = {
                @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDAO.class))),
                @ApiResponse(responseCode = "404", description = "Id invalido.", content = @Content(schema = @Schema(implementation = String.class))),
                @ApiResponse(responseCode = "403", description = "Não autorizado.")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(operationId = "listAllSales",
            summary = "Buscar e filtrar vendas.",
            description = "Busca todas as vendas ou filtra por alguns parametros. Não é possivel combinar os parametros, eles devem ser buscados separadamente. <br><br>" +
                          "- O parametro customer busca todas as vendas que tenham sido feitas por aquele cliente. <br><br>" +
                          "- O parametro seller busca todas as vendas que tenham algum produto daquele vendedor envolvido. <br><br>" +
                          "- O parametro product busca todas as vendas que tenham aquele produto envolvido. <br><br>" +
                          "*Apenas clientes ou vendedores autenticados",
            responses = {
                @ApiResponse(responseCode = "200", description = "Ok."),
                @ApiResponse(responseCode = "403", description = "Não autorizado.", content = @Content(mediaType = "null"))
            }
    )
    @GetMapping
    public Page<SaleDAO> listAllSales(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                      @RequestParam(required = false)Long customer,
                                      @RequestParam(required = false)Long seller,
                                      @RequestParam(required = false)Long product
                                      ) {
        return service.search(pageable, customer, seller, product);
    }
}
