package com.esdras.catalogo.videos.infrastructure.configuration;

import com.esdras.catalogo.videos.infrastructure.configuration.json.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//COMO OS ATRIBUTOS DE DOMINIO E CAMEL CASE, E ESPERA SNAKE CASE
//PRECISAMOS DESSE CABOCLO AQUI PRA FAZER A CONVERSÃO
//NO CASO DO PAGINATION E UM OBEJTO DE DOMINIO, NÃO PODEMOS COLOCAR UM JSON PROPERTY
//PRO OBJECT MAPPER USAR CONVERTER PRO SNAKE CASE, ISSO PROS TESTES E PRA VOLTA
@Configuration
public class ObjectMapperConfig {

    //COM ISSO UTILIZAMOS O OBJECT MAPPER PRA NOSSA APLICAÇÃP
    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }

}
