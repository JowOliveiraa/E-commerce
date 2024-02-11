package com.example.ecommerce.controllers;

import com.example.ecommerce.models.daos.ProductDAO;
import com.example.ecommerce.models.daos.RatingDAO;
import com.example.ecommerce.models.dtos.ProductDTO;
import com.example.ecommerce.models.dtos.RatingDTO;
import com.example.ecommerce.models.dtos.UpdateProductDTO;
import com.example.ecommerce.services.ProductService;
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
@RequestMapping("/product")
@Tag(name = "Controller de produto", description = "Trata todas as requests relacionadas a produtos.")
public class ProductController {

    @Autowired
    private ProductService service;


    @Operation(operationId = "registerProduct",
        summary = "Criar produto.",
        description = "Cria um produto. *Apenas vendedores autenticados.",
        security = @SecurityRequirement(name = "token"),
        responses = {
            @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDAO.class))),
            @ApiResponse(responseCode = "404", description = "Id do vendedor invalido.", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Não autorizado")
        }
    )
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ProductDTO dto) {
        return service.register(dto);
    }

    @Operation(operationId = "ratingProduct",
            summary = "Avaliar produto.",
            description = "Cria uma avaliação para o produto. O rating do produto é baseado na media das avaliações, a nota deve estar entre 1 e 5. *Apenas clientes autenticados.",
            security = @SecurityRequirement(name = "token"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingDAO.class))),
                    @ApiResponse(responseCode = "400", description = "Nota invalida.", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Id do produto ou cliente invalido", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Não autorizado")
            }
    )
    @PostMapping("/rating")
    public ResponseEntity<Object> rating(@RequestBody RatingDTO dto) {
        return service.rating(dto);
    }

    @Operation(operationId = "getProductById",
            summary = "Buscar produto por id.",
            description = "Busca um produto pelo id dele.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id do produto invalido", content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @Operation(operationId = "listAllProducts",
            summary = "Buscar ou pesquisar produtos.",
            description = "Busca todos os produtos de forma paginada. É possivel filtrar por nome, categoria, e vendedor de maneira separada ou combinando esses parametros.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json"))
            }
    )
    @GetMapping
    public Page<ProductDAO> listAllProducts(@PageableDefault(size = 10, page = 0)Pageable pageable,
                                            @RequestParam(required = false)String name,
                                            @RequestParam(required = false)String category,
                                            @RequestParam(required = false)Long seller
    ) {
        return service.search(pageable, name, category, seller);
    }

    @Operation(operationId = "listRatingsByProductId",
            summary = "Buscar avaliações de um produto.",
            description = "Busca de forma paginada todas as avaliações de um produto pelo id dele.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok.", content = @Content(mediaType = "application/json")),
            }
    )
    @GetMapping("/rating/{product}")
    public Page<RatingDAO> getRatings(@PageableDefault(size = 10, page = 0)Pageable pageable, @PathVariable Long product) {
        return service.getRatings(pageable, product);
    }

    @Operation(operationId = "updateProduct",
            summary = "Editar produto.",
            description = "Edita as informações de um produto pelo id dele. *Apenas vendedores autenticados.",
            security = @SecurityRequirement(name = "token"),
            responses = {
                    @ApiResponse(responseCode = "202", description = "Aceito.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDAO.class))),
                    @ApiResponse(responseCode = "404", description = "Id do produto invalido", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "403", description = "Não autorizado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateProductDTO dto) {
        return service.update(id,dto);
    }

}
