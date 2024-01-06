package com.esdras.catalogo.videos.infrastructure.category.presenters;

import com.esdras.catalogo.videos.application.category.retrieve.get.CategoryOutput;
import com.esdras.catalogo.videos.application.category.retrieve.list.CategoryListOutput;
import com.esdras.catalogo.videos.infrastructure.category.models.CategoryListResponse;
import com.esdras.catalogo.videos.infrastructure.category.models.CategoryResponse;

import java.util.function.Function;

public interface CategoryApiPresenter {

    //OU PODE SEER UMA CLASSE FINAL, COM UM CONTRUCTOR PRIVADO, COM OS METODOS ESTATICOS

    //OU ESSA ABORDAGEM QUE GOSTEI BASTANTE PROS MAPPERS E DA FORMA MAIS FUNCIONAL
    Function<CategoryOutput, CategoryResponse> present = output -> new CategoryResponse(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.updatedAt(),
            output.deletedAt()
    );

    Function<CategoryListOutput, CategoryListResponse> presentList = output -> new CategoryListResponse(
            output.id().getValue(),
            output.name(),
            output.description(),
            output.isActive(),
            output.createdAt(),
            output.deletedAt()
    );

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
