package com.esdras.catalogo.videos.infrastructure.api.controllers;

import com.esdras.catalogo.videos.application.category.create.CreateCategoryCommand;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryOutput;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryUseCase;
import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import com.esdras.catalogo.videos.infrastructure.api.CategoryAPI;
import com.esdras.catalogo.videos.infrastructure.category.models.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

//BOM DE USAR A INTERFACE, TENDO AS DEFINIÇOES E A DOCUMENTAÇÃO NA INTERFACE
// E AQUI SOBRESCREVER OS METODOS E UTILIZAR OS CASOS DE USO
@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
    }


    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {

        //O BOOLEAN DO COMMAND  E UM BOOLEAN PRIMITIVO, MAS COMO API INPUT E UM BOOLEAN DA CLASSE
        //ELE PODE SER NULO, POR ISSO PRECISA DE UMA TRATATIVA
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : false
        );
        //COMO ESTAMOS USANDO OS NOTIFICATION, ELE VAI RETORNAR UM EITHER

        //SE VIER ERRO, RETORNAR UMA UNPROCESSABLE ENTITY
        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        //SENÃO RETORNAR UM CREATED COMO VLAOR E O ID DA CATEGORIA CRIADA
        final Function<CreateCategoryOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/categories/" + output.id())).body(output);

        //O FOLD RETORNA TANTO O ERRO OU O SUCESSO
        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);

    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
