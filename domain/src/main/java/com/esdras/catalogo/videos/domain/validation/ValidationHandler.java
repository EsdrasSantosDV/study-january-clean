package com.esdras.catalogo.videos.domain.validation;

import java.util.List;

/*
SEGUIR O PADRAO STRATEGY
 */
public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler anHandler);

    ValidationHandler validate(Validation aValidation);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    public interface Validation {
        void validate();
    }
}
