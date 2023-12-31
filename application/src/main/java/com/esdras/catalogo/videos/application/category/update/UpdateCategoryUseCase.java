package com.esdras.catalogo.videos.application.category.update;

import com.esdras.catalogo.videos.application.UseCase;
import com.esdras.catalogo.videos.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification,UpdateCategoryOutput>> {


}
