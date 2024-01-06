package com.esdras.catalogo.videos.domain.exceptions;

import com.esdras.catalogo.videos.domain.AggregateRoot;
import com.esdras.catalogo.videos.domain.Identifier;
import com.esdras.catalogo.videos.domain.validation.Error;

import java.util.Collections;
import java.util.List;

/*
PRA REPRESENTAR UMA NOT FOUND
 */
public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        //PEGAMOS O AGREGADO E O NOME DA CLASSE E O DIZEMOS QUE PRA ESSE ID NÃO FOI ENCONTRADO NADA
        return new NotFoundException(anError, Collections.emptyList());
    }
}
