package com.esdras.catalogo.videos.infrastructure;

import com.esdras.catalogo.videos.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

// Esssa configuração e super importante para o spring boot funcionar
// Essa classe é a classe principal QUE INICIA A APLICAÇÃO
// SEM ELA NÃO FUNCIONA
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //ESSA CONFIG SETA A PROPRIEDADE COMO DEFAULT O DEVELOPMENT

        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);
    }

    //testar se a aplicação esta funcionando corretamente e ver se tem os beans pra cada use case
//    @Bean
//    @DependsOnDatabaseInitialization
//    ApplicationRunner runner(
//            @Autowired CreateCategoryUseCase createCategoryUseCase
//    ) {
//        return args -> {
//
//        };
//    }


}