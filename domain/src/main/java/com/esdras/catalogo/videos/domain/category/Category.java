package com.esdras.catalogo.videos.domain.category;

import com.esdras.catalogo.videos.domain.AggregateRoot;
import com.esdras.catalogo.videos.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

//ESSA CLASSE E UMA ENTIDADE QUE REPRESA UM AGREGADO RAIZ DE CATEGORIA DE VIDEO
//UM AGREGADO RAIZ E UM PONTO DE PARTIDA PRA COMPOSICAO DE ENTIDADES E OBJETOS DE VALOR
//NESSE CASO A ENTIDADE POSSUI UM CATEGORIA ID QUE E UM IDENTIFICADOR DE CATEGORIA
public class Category extends AggregateRoot<CategoryID> implements Cloneable {
    //ESSE CLONABLE E PRA REALIZAR UMA COPIA DE UMA ENTIDADE QUE RETORNE UMA NOVA REFERENCIA


    private String name;
    private String description;
    private boolean active;
    //VOCE JA COMECOU A USAR INSTANT
    //INSTANT E UM TIPO DE DATA QUE REPRESENTA UM MARCO NO TEMPO
    //ELE E UM TIPO DE DATA QUE E USADO PRA VC TRABALHAR COM DATA E HORA
    //SO LEVA EM CONTA UTC, MELHOR COISA PRA CASSOS QUE A PRECISÃO IMPORTAM MUITO
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryID id, final String name, final String description, final boolean active, final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        super(id);
        //NO CASO DE NULLABLE, O NOME NÃO PRECISMAOS VALIDAR, PQ E DEPENTENDE DO USUARIO
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is required");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt is required");
        this.deletedAt = deletedAt;
    }

    //DAR PREFERENCIA  A FACTORY METHODS AO INVES DE CONSTRUTORES
    //PORQUE FACTORY METHODS NOS DA MAIS FLEXIBILIDADE

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }


    public static Category with(
            final CategoryID anId,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                anId,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category with(final Category aCategory) {
        return with(
                aCategory.getId(),
                aCategory.name,
                aCategory.description,
                aCategory.isActive(),
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }


    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        this.name = aName;
        this.description = aDescription;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Category clone() {
        try {
            Category clone = (Category) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}