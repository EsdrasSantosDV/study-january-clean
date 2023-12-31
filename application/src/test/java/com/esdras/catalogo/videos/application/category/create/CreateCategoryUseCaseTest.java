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











}
