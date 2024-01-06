package com.esdras.catalogo.videos.infrastructure.api.controllers;

import com.esdras.catalogo.videos.application.category.create.CreateCategoryCommand;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryOutput;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryUseCase;
import com.esdras.catalogo.videos.application.category.delete.DeleteCategoryUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.list.ListCategoriesUseCase;
import com.esdras.catalogo.videos.application.category.update.UpdateCategoryCommand;
import com.esdras.catalogo.videos.application.category.update.UpdateCategoryOutput;
import com.esdras.catalogo.videos.application.category.update.UpdateCategoryUseCase;
import com.esdras.catalogo.videos.domain.category.CategorySearchQuery;
import com.esdras.catalogo.videos.domain.pagination.Pagination;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import com.esdras.catalogo.videos.infrastructure.api.CategoryAPI;
import com.esdras.catalogo.videos.infrastructure.category.models.CategoryResponse;
import com.esdras.catalogo.videos.infrastructure.category.models.CreateCategoryRequest;
import com.esdras.catalogo.videos.infrastructure.category.models.UpdateCategoryRequest;
import com.esdras.catalogo.videos.infrastructure.category.presenters.CategoryApiPresenter;
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
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;

    private final UpdateCategoryUseCase updateCategoryUseCase;

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    private final ListCategoriesUseCase listCategoriesUseCase;


    public CategoryController(final CreateCategoryUseCase createCategoryUseCase,
                              final GetCategoryByIdUseCase getCategoryByIdUseCase,
                              final UpdateCategoryUseCase updateCategoryUseCase,
                              final DeleteCategoryUseCase deleteCategoryUseCase,
                              final ListCategoriesUseCase listCategoriesUseCase) {
        this.createCategoryUseCase = Objects.requireNonNull(createCategoryUseCase);
        this.getCategoryByIdUseCase = Objects.requireNonNull(getCategoryByIdUseCase);
        this.updateCategoryUseCase = Objects.requireNonNull(updateCategoryUseCase);
        this.deleteCategoryUseCase = Objects.requireNonNull(deleteCategoryUseCase);
        this.listCategoriesUseCase = Objects.requireNonNull(listCategoriesUseCase);

    }


    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {

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
        return listCategoriesUseCase.execute(new CategorySearchQuery(page, perPage, search, sort, direction));
    }

    @Override
    public CategoryResponse getById(String id) {
        //DA MANEIRA PADRAO
        //return CategoryApiPresenter.present(getCategoryByIdUseCase.execute(id));
        // DA MANERIA FUNCIONAL QUE GOSTEI BASTANTE
        //CHAMA O METODO DEPOIS O PRESENT
        return CategoryApiPresenter.present.compose(getCategoryByIdUseCase::execute).apply(id);
    }

    @Override
    public ResponseEntity<?> updateById(String id, UpdateCategoryRequest input) {
        final var aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess =
                ResponseEntity::ok;

        return this.updateCategoryUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }


    @Override
    public void deleteById(final String anId) {
        this.deleteCategoryUseCase.execute(anId);
    }
}
