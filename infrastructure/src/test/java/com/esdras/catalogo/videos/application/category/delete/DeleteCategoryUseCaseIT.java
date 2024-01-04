package com.esdras.catalogo.videos.application.category.delete;

import com.esdras.catalogo.videos.IntegrationTest;
import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryJpaEntity;
import com.esdras.catalogo.videos.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

//UMA DICA E TESTAR OS CASOS DE TESTE QUE FIZ LA NO APLICATION, LA EU MOCKEI O COMPORTAMENTO, AQUI NÃO PRECISA
@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository categoryRepository;

    //PRA MOCKAR O COMPORTAMENTO DO GATEWAY ELE VAI SER SPYBEAN
    //PRA SERVIR NO REAL CONTEXTO DO SPRING
    @SpyBean
    private CategoryGateway categoryGateway;

    @Test
    @DisplayName("Dado um id válido, quando chamar o caso de uso de deletar categoria, então deve deletar a categoria")
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        save(aCategory);

        Assertions.assertEquals(1, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    @DisplayName("Dado um id inválido, quando chamar o caso de uso de deletar categoria, então deve retornar erro")
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
        final var expectedId = CategoryID.from("123");

        Assertions.assertEquals(0, categoryRepository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, categoryRepository.count());
    }

    @Test
    @DisplayName("Dado um id válido, quando chamar o caso de uso de deletar categoria, então deve retornar erro")
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Category... aCategory) {
        //CRIAR UM METODO QUE SALVA UMA LISTA DE CATEGORIAS
        //ELE VAI RECEBER UM ARRAY DE CATEGORIAS
        //ELE VAI CONVERTER ESSAS CATEGORIAS PARA ENTIDADES
        //E DEPOIS SALVAR ESSAS ENTIDADES NO BANCO
        categoryRepository.saveAllAndFlush(
                Arrays.stream(aCategory)
                        .map(CategoryJpaEntity::from)
                        .toList()
        );
    }
}
