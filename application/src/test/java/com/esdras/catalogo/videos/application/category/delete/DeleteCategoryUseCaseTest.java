package com.esdras.catalogo.videos.application.category.delete;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase deleteCategoryUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }


    //PRA DELETAR SEMPRE PENSAMOS EM 2 CASOS PRA DAR UMA BOA COBERTURA, E DADO ELES VAMOS MELHORANDO A COBERTURA
    //1º CASO DE USO E O CASO QUE DA TUDO CERTO E NÃO DAR NENHUM ERRO, E A QUANTIDADE DE VEZES QUE E CHAMADO NO GATEWAY E DADO A REGRA QUIE PENSAMOS
    //2º CASO DE USO E O QUE ENVIAMOS UM ID VALIDO E DA UM EXCEÇÃO NO GATEWAY E RETORNA A EXCEÇÃO


    @Test
    @DisplayName("Dado um id válido, quando chamar o deleteCategory, então deve ser ok")
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais daora", true);
        final var expectedId = aCategory.getId();

        doNothing()
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> deleteCategoryUseCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }



    @Test
    @DisplayName("Dado um id válido, quando chamar o deleteCategory, então deve retornar uma exceção")
    public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
        final var aCategory = Category.newCategory("Filmes", "A categoria mais daora", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> deleteCategoryUseCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }






}