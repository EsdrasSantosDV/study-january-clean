package com.esdras.catalogo.videos.application.category.retrieve.list;

import com.esdras.catalogo.videos.application.UseCase;
import com.esdras.catalogo.videos.domain.category.CategorySearchQuery;
import com.esdras.catalogo.videos.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase
        extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
