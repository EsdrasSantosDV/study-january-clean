package com.esdras.catalogo.videos;


import com.esdras.catalogo.videos.infrastructure.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

//DIFERENTE DOS DE GATEWARY QUE É DATA JPA TEST
//O DE INTEGRAÇÃO É SPRING BOOT TEST
//PERCORRENDO TODOS OS BEANS CRIADOS POR MIM NA CONFIGURAÇÃO DE BEASNS
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(CleanUpExtension.class)
public @interface IntegrationTest {

}
