package com.esdras.catalogo.videos.infrastructure.category.models;


import com.fasterxml.jackson.annotation.JsonProperty;

//ISSO QUE ESPERAMOS NA NOSSA API
//SERIA O DTO
public record CreateCategoryApiInput(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("is_active") Boolean active
) {
}
