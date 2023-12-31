package com.esdras.catalogo.videos.application.category.update;

import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import io.vavr.control.Either;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{
    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {

        return null;
    }
}
