package com.esdras.catalogo.videos.domain;


/*
São o que compoem uma entidade, porém não possuem identidade própria,
 são imutáveis eles tem vida no decorrer de vida de um software,
 eles represetam um valor seja ele uma identidade que representa um id,uma quantidade, um
 valor monetario
 */
/*
 Uma das coisas que nunca fazia, e recomendo vc meu eu do futuro seguir, seria o seguinte,
 sempre crie as abstraçoes de dominio, pra entidades pros valores agregados,
 fica muito mais facil pra analisar e identificar como o dominio se comporta,
 tanto pra definir um estilo e diretriz de software
 Ele e um objeto que pode compor varios outros tipos primitivo.
 Ele deve representar um conceito imutavel de dfominio, encapsulando em um objeto
 */
public abstract class ValueObject {
}
