package com.esdras.catalogo.videos.infrastructure.api;

import com.esdras.catalogo.videos.domain.pagination.Pagination;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//TANTO FAZ / OU SEM BARRA SPRING JA VE
@RequestMapping(value = "categories")
public interface CategoryAPI {
    //metodos que a api vai expor, e a documentação dela
    //RESPOSTA DE SUCESSO OU DE ERRO

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createCategory();

    //NO SPRING ELE JA FAZ O PROPRIO PARSE PRA GENTE PRA INT
    @GetMapping
    Pagination<?> listCategories(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "0") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "dir", required = false, defaultValue = "asc") final String direction

    );

}
