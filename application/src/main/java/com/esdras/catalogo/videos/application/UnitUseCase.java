package com.esdras.catalogo.videos.application;

//ESSE A CLASSE ABSTRATA UNIT USE CASE VAI SERVIR PROS CASOS QUE SO POLSSUI O COMANDO
public abstract class UnitUseCase<IN> {
    public abstract void execute(IN anIn);
}
