package com.esdras.catalogo.videos.domain;

import com.esdras.catalogo.videos.domain.validation.ValidationHandler;

import java.util.Objects;

/*
Uma entidade e uma abstração de negocio que possui um identidade
e que possui um ciclo de vida, ou seja, ela é criada, ela é modificad
mas uma entidade nem sempre vai ter um repositorio, ai que diferencia
um agregado de uma entidade, um agregado igual naquele exemplo
que eu fiz la traz, um agregado seria uma composição de entidades que
determianm um aspecto de dominio, por exemplo, um pedido
 */
public abstract class Entity<ID extends Identifier>{

//    COMO EU DISSE UJMA ENTIDADE POSSUI UM  IDENTIIFCIADOR
    protected final ID id;

    protected Entity(final ID id){
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

//    AQUI EU DEFINO QUE UMA ENTIDADE E IGUAL A OUTRA SE ELAS TIVEREM O MESMO ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
