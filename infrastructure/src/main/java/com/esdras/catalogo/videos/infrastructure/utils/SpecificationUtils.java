package com.esdras.catalogo.videos.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

//PRA NÃO EXTENDER
public final class SpecificationUtils {
    private SpecificationUtils() {
    }

    //PRA NÃO FICAR REPETINDO O CODIGO
    //APLICAR UM SPECIFICATION GENERICO, PRA FAZER OS FILTROS COMPARTILHADOS DE BUSCA
    //PRA NÃO FICAR REPETINDO O CODIGO
    //PESQUISAR POR NOME, OU POR DESCRIPTION OU PRA QUALQUER OUTRO PARAMETRO
    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), like(term.toUpperCase()));
    }

    private static String like(final String term) {
        return "%" + term + "%";
    }
}
