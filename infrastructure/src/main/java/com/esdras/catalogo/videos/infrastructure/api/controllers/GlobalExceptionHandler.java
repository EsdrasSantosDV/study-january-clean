package com.esdras.catalogo.videos.infrastructure.api.controllers;

import com.esdras.catalogo.videos.domain.exceptions.DomainException;
import com.esdras.catalogo.videos.domain.exceptions.NotFoundException;
import com.esdras.catalogo.videos.domain.validation.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//PRA TRATAR AS EXCEPTIONS
// UM HANDLER, E PRA CADA EXCEPTIONS VAMOS TRATAR BEM
//ISSO AUQI E BOM QUE SO A PORRA, O CONTROLLER NÃO PRECISA FICAR COLOCANDO AS EXCEPTIONS LA
@RestControllerAdvice
public class GlobalExceptionHandler {

    //VAMOS DEFINIR TODOS OS HANDLERS DE EXCEPTIONS

    //POR EXEMPLO PRA DOMAIN EXCEPTION VAMOS FAZER
    //DIZENDO PRO SPRING QUE QUANDO DER UMA DOMAIN EXCEPTION, O QUE ELE TEM QUE FAZER
    //PODE SER VARIOS EXCEPITONS PRA UM MESMO HANDLER
    @ExceptionHandler(value = DomainException.class)
    //NÃO VAMOS USAR, MAIS PODEMOS INJETAR EM OUTRO CASO PRA MANIPULAR O PAYLOAD COM ESSES
    //HTTP SERVLET
    public ResponseEntity<?> handleDomainException(
            final DomainException ex,
            final HttpServletRequest request,
            final HttpServletResponse requestResponse
    ) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }

    record ApiError(String message, List<Error> errors) {
        static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }
}
