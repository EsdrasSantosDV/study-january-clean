package com.esdras.catalogo.videos;


import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

//ABSTRAIR O QUE LIMPA O BANCO DE DADOS H2 A CADA TESTE
public class CleanUpExtension implements BeforeEachCallback {

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