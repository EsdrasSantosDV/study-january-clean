package com.esdras.catalogo.videos.infrastructure.api;

import com.esdras.catalogo.videos.ControllerTest;
import com.esdras.catalogo.videos.application.category.create.CreateCategoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//ESSA ANOTAÇÃO CONTROLLER TESTE, SO VAI FAZER O SCAN DOS CONTROLLERS
//E REST CONTROLELRS, E NÃO DOS SCANS DE SERVICES E COMPONENTES
@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    //PRECISAMOS DO MOCK MVC, PRA FAZER AS REQUISIÇÕES
    @Autowired
    private MockMvc mockMvc;

    //PRECISAMOS MOCKAR OS BEANS DOS USE CASES, SE NÃO FODE TUDO
    //PORQUE ELES NÃO VÃO ESTAR NO CONTEXTO DE TESTE DO SPRING, O WEB MVC TEST QUE TA NA NOTAÇÕA
    //CONTROLLER TEST SO SERVE PRA TESTAR CONTROLLERS E REST CONTROLLERS E NÃO OS BEANS
    //QUE AQUELA CONTRROLLER NESSECTIA

    //PRECISAMOS MOCKAR OS BEANS DOS USE CASES, SE NÃO FODE TUDO
    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;


    @Test
    public void test() {

    }


}
