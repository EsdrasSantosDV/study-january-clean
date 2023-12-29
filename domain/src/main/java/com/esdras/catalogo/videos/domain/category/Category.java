package com.esdras.catalogo.videos.domain.category;

import com.esdras.catalogo.videos.domain.AggregateRoot;

import java.time.Instant;

//ESSA CLASSE E UMA ENTIDADE QUE REPRESA UM AGREGADO RAIZ DE CATEGORIA DE VIDEO
//UM AGREGADO RAIZ E UM PONTO DE PARTIDA PRA COMPOSICAO DE ENTIDADES E OBJETOS DE VALOR
//NESSE CASO A ENTIDADE POSSUI UM CATEGORIA ID QUE E UM IDENTIFICADOR DE CATEGORIA
public class Category extends AggregateRoot<CategoryID> {


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
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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
}