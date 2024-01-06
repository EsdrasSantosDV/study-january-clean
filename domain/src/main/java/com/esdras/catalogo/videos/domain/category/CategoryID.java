package com.esdras.catalogo.videos.domain.category;

import com.esdras.catalogo.videos.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

/*
Essa abastração serve pra definir um identificador pra categoria
, tanto pra depois vc gerar a identidade, tanto pra vir do banco pra colocar pro dominio
 */
public class CategoryID extends Identifier {

    private final String value;

    private CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique() {
        return CategoryID.from(UUID.randomUUID());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    public static CategoryID from(final UUID anId) {
        return new CategoryID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }


}
