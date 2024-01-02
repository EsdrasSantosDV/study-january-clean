package com.esdras.catalogo.videos.infrastructure;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;


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
@ExtendWith(MySQLGatewayTest.CleanUpExtensions.class)
public @interface MySQLGatewayTest {

    class CleanUpExtensions implements BeforeEachCallback {
        //ISSO PRA LIMPAR O BANCO DE DADOS ANTES DE CADA TESTE


        @Override
        public void beforeEach(final ExtensionContext context) {
            //ISSO LIMPA TODOS OS REPOSTIORIES DANDO UM DELETE PRA CADA UM, DIRETAMENTE DO MAIS ALTO NIVEL
            //QUE E O CRUD REPOSITORY
            //PQ O REPOSITORYU NÕ TEM METODOS
            final var repositories = SpringExtension
                    .getApplicationContext(context)
                    .getBeansOfType(CrudRepository.class)
                    .values();

            cleanUp(repositories);
        }

        private void cleanUp(final Collection<CrudRepository> repositories) {
            repositories.forEach(CrudRepository::deleteAll);
        }
    }
}
