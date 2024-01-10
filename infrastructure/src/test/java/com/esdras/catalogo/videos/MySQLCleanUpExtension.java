package com.esdras.catalogo.videos;


import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

//ABSTRAIR O QUE LIMPA O BANCO DE DADOS H2 A CADA TESTE
public class MySQLCleanUpExtension implements BeforeEachCallback {

    //ISSO PRA LIMPAR O BANCO DE DADOS ANTES DE CADA TESTE
    @Override
    public void beforeEach(final ExtensionContext context) {
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