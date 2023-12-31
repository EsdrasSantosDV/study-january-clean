package com.esdras.catalogo.videos.application;

//ESSE A CLASSE ABSTRATA UNIT USE CASE VAI SERVIR PROS CASOS QUE SO POLSSUI O RETORNO
//TIPO UM CASO DE USO QUE SO RETORNA UMA LISTA
public abstract class NullaryUseCase<OUT> {
    public abstract OUT execute();
}
