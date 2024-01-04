package com.esdras.catalogo.videos.infrastructure.configuration.usecases;

import com.esdras.catalogo.videos.application.category.create.CreateCategoryUseCase;
import com.esdras.catalogo.videos.application.category.create.DefaultCreateCategoryUseCase;
import com.esdras.catalogo.videos.application.category.delete.DefaultDeleteCategoryUseCase;
import com.esdras.catalogo.videos.application.category.delete.DeleteCategoryUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.list.ListCategoriesUseCase;
import com.esdras.catalogo.videos.application.category.update.DefaultUpdateCategoryUseCase;
import com.esdras.catalogo.videos.application.category.update.UpdateCategoryUseCase;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

//CONFIGURAR TODOS OS BEANS DE USECASES
//E FAZER A INJEÇÃO PELO CONSTRUTIOR, PRA FICAR JOIA
//EXISTE OUTRA POSSIBILIDADE QUE E IMPORTNADO O JAVAX, NA APLICATION, E COLOCAR EMCIMA DE CADA USE CASE DE IMPLEMENTAÇÕA
// UM NAMED, MAS ESSA DO CONFIGURATION E PERFEITA
@Configuration
public class CategoryConfigurationUseCase {

    //COLOCAR OS GATEWAY, ANALOGIA DO TCC QUE FIZ, ONDE COLOCAVA AS PORTAS DE SAIDA
    //AQUI VAI TODOS OS GATEWAYS
    private final CategoryGateway categoryGateway;

    public CategoryConfigurationUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    //AQUI VAI TODOS OS BEANS DE USECASES
    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

}
