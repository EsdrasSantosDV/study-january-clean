package com.esdras.catalogo.videos.infrastructure.api;

import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.infrastructure.category.models.CreateCategoryApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TANTO FAZ / OU SEM BARRA SPRING JA VE
@RequestMapping(value = "categories")
@Tag(name = "Categories")
public interface CategoryAPI {
    //metodos que a api vai expor, e a documentação dela
    //RESPOSTA DE SUCESSO OU DE ERRO
    //FICA MUITO MAIS CLEAN E FACIL DE ENTENDER
    //SEPARAR ISSO DO CONTROLLER E FICA BEM MAIS CLEAN

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
        //TEM MUITAS MAIS CONFIGURAÇOES DE DOCUMENTAÇÃO
        //cCOM O REQUEST BODY
    ResponseEntity<?> createCategory(@RequestBody CreateCategoryApiInput input);

    //NO SPRING ELE JA FAZ O PROPRIO PARSE PRA GENTE PRA INT
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "422", description = "A invalid parameter was received"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown"),
    })
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "0") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction

    );

}
