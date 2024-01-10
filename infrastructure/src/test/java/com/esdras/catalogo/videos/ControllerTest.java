package com.esdras.catalogo.videos;


import com.esdras.catalogo.videos.infrastructure.configuration.ObjectMapperConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

//ESSE AQUI E UMA ESTRATEGVIA DE TEST PARECIDA COMA D E INTEGRATION E DE MYSQL
//SO QUE O CONTEXTO E PRA TESTAR AS CONTROLLER
//VENDO SE OS PARSES DOS PARAMETROS VIERAM CORRETOS
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
//ESSES SÃO PADROES OS DE CIMA
@WebMvcTest
//PRA CONFIGURAR O QUE VAI SER TESTADO EM RELAÇÃO A DESSERIZAÇÃO E SERIALIZAÇÃO
//E MELHORAS OS TESTES
//SO QUE ESSA CLASSE NÃO FOI SO PRA ENCAPSULAR O WEB MVC TEST
//TAMBEM FOI PRA OCNFIGURAR O OBJET MAPPER
@Import(ObjectMapperConfig.class)
public @interface ControllerTest {
    //VAI PEGAR AS ANOTAÇOES DE CONTROLLER E SERVIR COMO ESTREATEGIA DE TESTE
    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};
}
