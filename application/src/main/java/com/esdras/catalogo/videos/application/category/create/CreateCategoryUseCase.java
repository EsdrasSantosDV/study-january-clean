package com.esdras.catalogo.videos.application.category.create;

import com.esdras.catalogo.videos.application.UseCase;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import io.vavr.control.Either;

//PRA CADA USE CASE EXTENDER A ABSTRAÇÃO DEFINIDA ANTERIORMENTE, NESSE CASO, ESTAMOS O EITHER
//QUE É O RESULTADO DA EXECUÇÃO DO USE CASE
//O EITHER PODE RETORNAR UM ERRO OU UM RESULTADO
//NO CASO, O ERRO E UMA NOTIFICATION, E O RESULTADO E O OUTPUT
//NOTIFICATION TEM TODOS OS POSSIVEIS ERROS QUE NOS FOMOS COLOCANDO
public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutput>> {
}
