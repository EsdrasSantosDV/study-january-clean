package com.esdras.catalogo.videos.domain;

/*
Essa abastração serve pra definir um identificador, tanto pra depois vc gerar a identidades
de entidadae
 */
public abstract class Identifier extends ValueObject {
    public abstract String getValue();
}
