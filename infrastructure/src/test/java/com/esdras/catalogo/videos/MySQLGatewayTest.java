package com.esdras.catalogo.videos;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;


// ISSO PRA CONFIGURAR OS BEANS POR PADRÃO
//QUE TEM ESSE MYSQL Gateway
//EM TEMPO DE EXECÇÃO DO TESTE
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
// ISSO PRA CONFIGURAR OS BEANS POR PADRÃO
//QUE TEM ESSE MYSQL Gateway
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]")
})
@DataJpaTest
@ExtendWith(CleanUpExtension.class)
public @interface MySQLGatewayTest {

}
