package com.esdras.catalogo.videos.application.category.create;

import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

//ISSO AQUI AJUDA BASTANTE
//PQ NÃO PRECISA FICAR FAZENDO TANTA CONFIG NO MOCKITÃO
//ELE JA FAZ BASTANTE COISA PRA GENTE
//NAQUELES PROJETOS ANTES QUE EU ESTUDEI, DAVA MUITO TRABALHO
@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    //PRA SERVIR PRO INJECTION SYSTEM PRA COLOCAR
    //AS DEPENDENCIAS NECESSARIAS MOCKADAS PRO NOSSOS CASOS DE USO
    @InjectMocks
    private DefaultCreateCategoryUseCase createCategoryUseCase;

    //PRAS DEPENDENCIAS QUE O CASO DE USO PRECISA
    //ELE VAI PEGAR DO INJECTION SYSTEM
    //PRA MEIO QUE AS PORTAS
    //ISSO AQUI VAI MOCKAR OS COMPORTAMENTOS NECESSARIOS
    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        //AQUI VAMOS FAZER AS CONFIGURAÇÕES NECESSARIAS
        //PRA CADA TESTE
        //PQ SE NÃO FIZER ISSO, VAI DAR PROBLEMA
        //PQ O MOCKITÃO VAI FICAR GUARDANDO OS COMPORTAMENTOS
        //E VAI DAR PROBLEMA
        //VAMOS REDEFINIR O CATEGORYGateway pra cada teste
        Mockito.reset(categoryGateway);
    }

    //AQUI VAMOS FAZER OS TESTES
    //PRA CRIAÇÃO
    //APLICANDO AS TECNCIAS DE DDD
    //PROS CASOS PRINCIPAIS

    //1º CASO DE USO E O CASO QUE DA TUDO CERTO
    //CASO DE USO DE SUCESSO
    //CASO FELIZ ONDE MANDAMOS UM COMANDO VALIDO
    //E TUDO PENSAMOS ORIENTADO QUE VAI DAR CERTO
    //SE FOI CHAMADO A QUANTIDADE DE VEZES CORRETA
    //SE FOI CHAMADO COM OS PARAMETROS CORRETOS
    //SE FOI CHAMADO NA ORDEM CORRETA
    //SE OS RETORNOS SÃO OS ESPERADOS
    //SE OS COMPORTAMENTOS SÃO OS ESPERADOS
    //SE OS OBJETOS SÃO OS ESPERADOS

    //OUTRA COISA QUE E BOM SEGUIR A NOMECLATURA GIVEN, WHEN, THEN
    /*
    Em resumo, essa nomenclatura é uma combinação de:

    Condição Inicial (Given): O estado ou contexto inicial.
    Ação (When): O que está sendo feito ou testado.
    Resultado Esperado (Should): O que se espera que aconteça como resultado da ação.

     */
    @Test
    @DisplayName("Dado um Comando Válido, Quando Chama CreateCategory, Deve Retornar o Id da Categoria")
    public void  givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId()
    {
        final var expectedName = "ESDRAS KHAN";
        final var expectedDescription = "Dragon ball dasd";
        final var expectedIsActive= true;

        //AQUI VAMOS CRIAR O COMANDO VALIDO
        //QUE VAI SER PASSADO PRO CASO DE USO
        //E VAMOS PASSAR OS PARAMETROS
        final var aValidCommand =  CreateCategoryCommand.with(expectedName,expectedDescription,expectedIsActive);

        //DEVE RETORNAR O ID DA CATEGORIA CRIADA
        when(categoryGateway.create(any())).thenAnswer(
                returnsFirstArg()
        );

        final var actualOutput= createCategoryUseCase.execute(aValidCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        //O método verify é usado para checar se certas interações com o mock ocorreram.
        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedIsActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));

    }



    //2º CASO DE USO E O CASO QUE DE ENVIA UM COMANDO INVALIDO E ESPERA QUE DE UM ERRO

    @Test
    @DisplayName("Dado um Comando Inválido, Quando Chama CreateCategory, Deve Retornar uma Exception")
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais daora";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        //AQUI VAMOS CRIAR O COMANDO INVALIDO
        //E SEMPRE VER SE A QUANITDADE DE ERROS QUE A GENTE TA ESPERANDO DA
        //E SE A MENSAGEM DO ERRO E A MENSAGEM QUE A GENTE ESPERA


        final var aCommand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var notification = createCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        //SE O GATEWAY NÃO FOI CHAMADO QUER DIZER QUE DEU ERRO, E NEM PRO GATEWAY BATEU PRA CRIAR
        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    //3º CASO DE USO SEMPRE COMEÇAMOS COM OS TESTES DE NEGOCIO
    //MAS O PRIMEIRO E SEGUNDO E PADRÃO PRA DAR UMA MELHOR COBERTURA
    //
    @Test
    @DisplayName("Dado um Comando Inválido com uma categoria intaiva, Quando Chama CreateCategory, Deve Retornar uma categoria inativa")
    public void givenAValidCommandWithInactiveCategory_whenCallsCreateCategory_shouldReturnInactiveCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais daora";
        final var expectedIsActive = false;
        //VIMOS SE A CATEGORIA FOI CRIADA COMO INVATIVA E ENTROU CONMO INATIVA

        final var aCommand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = createCategoryUseCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedIsActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.nonNull(aCategory.getDeletedAt())
        ));
    }

    @Test
    @DisplayName("Dado um Comando Valido, Quando o gateway da erro, Deve Retornar uma exception")
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais daora";
        final var expectedIsActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = createCategoryUseCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                        && Objects.equals(expectedDescription, aCategory.getDescription())
                        && Objects.equals(expectedIsActive, aCategory.isActive())
                        && Objects.nonNull(aCategory.getId())
                        && Objects.nonNull(aCategory.getCreatedAt())
                        && Objects.nonNull(aCategory.getUpdatedAt())
                        && Objects.isNull(aCategory.getDeletedAt())
        ));
    }


}
