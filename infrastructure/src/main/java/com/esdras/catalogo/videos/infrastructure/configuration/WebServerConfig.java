package com.esdras.catalogo.videos.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//PRA CONFIGURAR OS BEANS POR PADRÃO
//O BEAN E UMA CLASSE QUE O SPRING GERENCIA
//ESSE COMPONENT SCAN, ELE SCANEA AS CLASSES QUE TEM AS ANOTAÇOES DO SPRING QUE SERVEM PRA GERAR UM
// BEAN ESTÃO DENTRO DO PACOTE
//O BEAN E UMA CLASSE CONFIGURADA PELO SPRING QUE E USADA PRA INSTANCIAR OUTRAS CLASSES
//ELA E GERENICADA PELO PROPRIO SPRING
//ELES SÃO GERENCIADOS PELO CONTAINER E NÃO PELO DESENVOLVEDOR
@Configuration
@ComponentScan("com.esdras.catalogo.videos")
public class WebServerConfig {

}
