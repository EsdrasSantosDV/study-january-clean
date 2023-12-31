package com.esdras.catalogo.videos.application.category.create;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

//O USE CASE E A IMPLEMENTAÇÃO DO CASO DE USO
//AQUI E ONDE VAI TER A REGRA DE NEGOCIO
//AQUI E ONDE ORQUESTRA AS OPERAÇOES DE NEGOCIOS, INSTANCIANDO AS CLASSES DE DOMINIO
//E CHAMANDO OS METODOS DE DOMINIO
//O EITHER E TOTALEMNTE FUNCIONAL
//O EITHER E UM MONAD, ELE PODE SER UM LEFT OU UM RIGHT
//O LEFT E UM ERRO, E O RIGHT E UM RESULTADO
//NO CASO, O LEFT E UMA NOTIFICATION, E O RIGHT E O OUTPUT
//NOTIFICATION TEM TODOS OS POSSIVEIS ERROS QUE NOS FOMOS COLOCANDO
public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryCommand aCommand) {
        //UMA COISA QUE EU GOSTEI MUITO DO VAR E QUE ELE NOS AJUDA A NÃO REPETIR O TIPO
        //ELE INFERE O TIPO PRA GENTE
        //COMEÇA A USAR MAIS ELE PRA GENTE NÃO FICAR REPETINDO O TIPO

        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();



        final var notification = Notification.create();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        aCategory.validate(notification);


        return notification.hasError() ? Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category aCategory) {
        return Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
