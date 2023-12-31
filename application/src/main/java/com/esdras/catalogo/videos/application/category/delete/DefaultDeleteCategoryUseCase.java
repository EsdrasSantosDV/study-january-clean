package com.esdras.catalogo.videos.application.category.delete;

import com.esdras.catalogo.videos.domain.category.CategoryGateway;

import java.util.Objects;

//OUTRA COISA QUE PODEMOS FAZER E CRIAR O ESQUELETO DO USE CASE, PRIMEIRO, SEM LOGICA, TESTAR E DEPOIS IMPLEMENTAR
public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }


    @Override
    public void execute(String anIn) {

    }
}
