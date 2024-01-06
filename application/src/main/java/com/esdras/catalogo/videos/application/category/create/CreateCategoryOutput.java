package com.esdras.catalogo.videos.application.category.create;

import com.esdras.catalogo.videos.domain.category.Category;
import com.esdras.catalogo.videos.domain.category.CategoryID;

//O OUTPUT E O QUE A APLICAÇÃO VAI RETORNAR
//BOM USAR O FROM PARA CONVERTER O DOMAIN PARA O OUTPUT
public record CreateCategoryOutput(CategoryID id) {
    public static CreateCategoryOutput from(final CategoryID categoryID) {
        return new CreateCategoryOutput(categoryID);
    }

    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
