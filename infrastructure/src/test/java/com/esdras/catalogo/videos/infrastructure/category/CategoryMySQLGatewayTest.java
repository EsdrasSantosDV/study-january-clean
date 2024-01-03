package com.esdras.catalogo.videos.infrastructure.category;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import com.esdras.catalogo.videos.infrastructure.MySQLGatewayTest;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {
    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Dado uma categoria válida, quando chamar o método create, então deve retornar uma nova categoria")
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        //ANTES VE SE TEM NENHUMA CATEGORIA
        Assertions.assertEquals(0, categoryRepository.count());

        final var actualCategory = categoryGateway.create(aCategory);

        //DEPOIS VE SE TEM UMA CATEGORIA

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        // ESSA PARTE E PRA VER SE SALVOU NO BANCO DE DADOS MESMO COM OS DADOS CORRETOS
        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    @DisplayName("Dado uma categoria válida, quando chamar o método update, então deve retornar uma categoria atualizada")
    public void givenAValidCategory_whenCallsUpdate_shouldReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais foda";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory("Film", null, expectedIsActive);

        //VER QUANTO TINHA ANTES
        Assertions.assertEquals(0, categoryRepository.count());
        //PERISTIR
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));
        //VER SE PERSISTIU
        Assertions.assertEquals(1, categoryRepository.count());

        //TEM QUE DAR O GET PRA PEGAR PQ AQUI E OPTIONAL O RETORNO
        final var actualInvalidEntity = categoryRepository.findById(aCategory.getId().getValue()).get();

        Assertions.assertEquals("Film", actualInvalidEntity.getName());
        Assertions.assertNull(actualInvalidEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualInvalidEntity.isActive());

        final var aUpdatedCategory = aCategory.clone()
                .update(expectedName, expectedDescription, expectedIsActive);

        //CRIAR O OBJETO ATUALIZADO
        final var actualCategory = categoryGateway.update(aUpdatedCategory);

        //ATUALIZAR
        Assertions.assertEquals(1, categoryRepository.count());

        //VER SE ATUALIZOU
        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        //COMPARAR SE A DATA DE ATUALIZAÇÃO E MENOR QUE A ATUAL
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());

        final var actualEntity = categoryRepository.findById(aCategory.getId().getValue()).get();
        //VER SE ATUALIZOU NO BANCO DE DADOS

        Assertions.assertEquals(aCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aCategory.getUpdatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertEquals(aCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    @DisplayName("Dado uma categoria válida, quando chamar o método deleteById, então deve retornar uma categoria deletada")
    public void givenAPrePersistedCategoryAndValidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        final var aCategory = Category.newCategory("Filmes", null, true);

        //PRA TESTAR SE DELETOU MESMO
        //VIMOS SE TA 0 ANTES
        Assertions.assertEquals(0, categoryRepository.count());

        //SALVA
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        //VE SE ADICIONOU
        Assertions.assertEquals(1, categoryRepository.count());

        //DELETAR USANDO O GATEWAY
        categoryGateway.deleteById(aCategory.getId());

        //E VER SE DELETOU MESMO
        Assertions.assertEquals(0, categoryRepository.count());
    }


    @Test
    @DisplayName("Dado uma categoria inválida, quando chamar o método deleteById, então deve retornar uma categoria deletada")
    public void givenInvalidCategoryId_whenTryToDeleteIt_shouldDeleteCategory() {
        //VE SE ADICIONOU
        Assertions.assertEquals(0, categoryRepository.count());

        //DELETAR USANDO O GATEWAY
        categoryGateway.deleteById(CategoryID.from("id invalido"));

        //E VER SE DELETOU MESMO
        Assertions.assertEquals(0, categoryRepository.count());
    }

}