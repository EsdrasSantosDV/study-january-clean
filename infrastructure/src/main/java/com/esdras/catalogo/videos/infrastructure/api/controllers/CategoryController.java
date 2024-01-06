package com.esdras.catalogo.videos.infrastructure.api.controllers;

import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

//BOM DE USAR A INTERFACE, TENDO AS DEFINIÇOES E A DOCUMENTAÇÃO NA INTERFACE
// E AQUI SOBRESCREVER OS METODOS E UTILIZAR OS CASOS DE USO
@RestController
public class CategoryController implements CategoryAPI {

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
