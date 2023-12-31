package com.esdras.catalogo.videos.application.category.retrieve.list;

import com.esdras.catalogo.videos.domain.category.CategoryGateway;
import com.esdras.catalogo.videos.domain.category.CategorySearchQuery;
import com.esdras.catalogo.videos.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }
    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aCommand) {
        return null;
    }
}
