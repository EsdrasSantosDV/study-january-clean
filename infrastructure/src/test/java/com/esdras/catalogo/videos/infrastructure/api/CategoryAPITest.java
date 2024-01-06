package com.esdras.catalogo.videos.infrastructure.api;

import com.esdras.catalogo.videos.ControllerTest;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryOutput;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryUseCase;
import com.esdras.catalogo.videos.application.category.retrieve.get.CategoryOutput;
import com.esdras.catalogo.videos.application.category.retrieve.get.GetCategoryByIdUseCase;
import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryID;
import com.esdras.catalogo.videos.domain.exceptions.DomainException;
import com.esdras.catalogo.videos.domain.exceptions.NotFoundException;
import com.esdras.catalogo.videos.domain.validation.Error;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import com.esdras.catalogo.videos.infrastructure.category.models.CreateCategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//ESSA ANOTAÇÃO CONTROLLER TESTE, SO VAI FAZER O SCAN DOS CONTROLLERS
//E REST CONTROLELRS, E NÃO DOS SCANS DE SERVICES E COMPONENTES
@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {


    //PRECISAMOS DO MOCK MVC, PRA FAZER AS REQUISIÇÕES
    @Autowired
    private MockMvc mvc;
    //PRECISAMOS MOCKAR OS BEANS DOS USE CASES, SE NÃO FODE TUDO
    //PORQUE ELES NÃO VÃO ESTAR NO CONTEXTO DE TESTE DO SPRING, O WEB MVC TEST QUE TA NA NOTAÇÕA
    //CONTROLLER TEST SO SERVE PRA TESTAR CONTROLLERS E REST CONTROLLERS E NÃO OS BEANS
    //QUE AQUELA CONTRROLLER NESSECTIA
    //PRECISAMOS MOCKAR OS BEANS DOS USE CASES, SE NÃO FODE TUDO

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;


    @Test
    @DisplayName("Dado um comando válido, quando chamar o caso de uso de criar categoria, então deve retornar o id da categoria")
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var apiInput =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        //precisamos retornar um either
        when(createCategoryUseCase.execute(any()))
                .thenReturn(Right(CreateCategoryOutput.from("123")));

        //PRECISAMOS DA MOCK MVC REQUEST BUILDERS
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(apiInput));

        //VAMOS SIMULAR UM POST
        //ELE VAI PERFORMAAR UMA AÇÃO

        this.mvc.perform(request).andDo(print()).andExpectAll(
                status().isCreated(),
                header().string("Location", "/categories/123"),
                header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                jsonPath("$.id", equalTo("123"))
        );

        //VERIFICAR SE O USE CASE FOI CHAMADO UMA VEZ, E SE OS VALORES DOS ARGUMENTOS TEM O MESMO VALOR DO COMAMNDO CERTO

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Dado um comando com nome nulo, quando chamar o caso de uso de criar categoria, então deve retornar uma exceção")
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnNotification() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var apiInput =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        //precisamos retornar um either
        when(createCategoryUseCase.execute(any()))
                .thenReturn(Left(Notification.create(new Error(expectedMessage))));

        //PRECISAMOS DA MOCK MVC REQUEST BUILDERS
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(apiInput));

        //VAMOS SIMULAR UM POST
        //ELE VAI PERFORMAAR UMA AÇÃO

        final var response = this.mvc.perform(request)
                .andDo(print());

        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));


        //VERIFICAR SE O USE CASE FOI CHAMADO UMA VEZ, E SE OS VALORES DOS ARGUMENTOS TEM O MESMO VALOR DO COMAMNDO CERTO

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Dado um comando invalido, quando chama o caso de uso de criar categoria, então deve retornar uma exceção")
    public void givenAInvalidCommand_whenCallsCreateCategory_thenShouldReturnDomainException() throws Exception {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedMessage = "'name' should not be null";

        final var apiInput =
                new CreateCategoryRequest(expectedName, expectedDescription, expectedIsActive);

        when(createCategoryUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        // when
        final var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(apiInput));

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedMessage)));

        verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedDescription, cmd.description())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    @DisplayName("Dado um id ,quando chamar o get by id de categoria, retornar a categoria")
    public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() throws Exception {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aCategory =
                Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = aCategory.getId().getValue();

        when(getCategoryByIdUseCase.execute(any()))
                .thenReturn(CategoryOutput.from(aCategory));

        // when
        final var request = get("/categories/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.description", equalTo(expectedDescription)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aCategory.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aCategory.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", equalTo(aCategory.getDeletedAt())));

        verify(getCategoryByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    @DisplayName("Dado um id invalido,quando chamar o get by id de categoria, retornar not found")
    public void givenAInvalidId_whenCallsGetCategory_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedId = CategoryID.from("123");

        when(getCategoryByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Category.class, expectedId));

        // when
        final var request = get("/categories/{id}", expectedId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(request)
                .andDo(print());

        // then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }


}
