package com.esdras.catalogo.videos.domain.exceptions;

import com.esdras.catalogo.videos.domain.validation.Error;

import java.util.List;

//AO INVES DE FICAR DANDO VARIOS ERROS, VAMOS GERAR UMA LISTA DE ERROS DE UMA VEZ
//E VAMOS LANÇAR UMA EXCEÇÃO COM ESSA LISTA DE ERROS
public class DomainException extends NoStacktraceException{
    private final List<Error> errors;

    private DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final Error anErrors) {
        return new DomainException(anErrors.message(), List.of(anErrors));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
