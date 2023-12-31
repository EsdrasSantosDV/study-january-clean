package com.esdras.catalogo.videos.application.category.create;


//UM COOMAMNDO E QUE INICIA A CONVERSA COM NOSSA APLICAÇÃO, ELE QUE DEFINE O QUE A APLICAÇÃO ESPERA
//ELE QUE DEFINE O QUE A APLICAÇÃO VAI RECEBER
//UMA BOA PRATICA E, REALIZAR OS FACTORIES DENTRO DO COMMAND,
//E USAR RECORDS, QUE SÃO IMUTAVEIS, E NÃO PRECISAMOS NOS PREOCUPAR COM O CONSTRUTOR
public record CreateCategoryCommand(
        String name,
        String description,
        boolean isActive
) {
    public static CreateCategoryCommand with(final String aName, final String aDescription, final boolean isActive) {
        return new CreateCategoryCommand(aName, aDescription, isActive);
    }
}
