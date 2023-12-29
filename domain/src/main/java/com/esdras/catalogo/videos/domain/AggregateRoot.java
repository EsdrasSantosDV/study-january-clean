package com.esdras.catalogo.videos.domain;

/*
o agregador raiz e um ponto de partida pra compor entidades e e objetos de valro
 */
public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {

        protected AggregateRoot(final ID id) {
            super(id);
        }
}
