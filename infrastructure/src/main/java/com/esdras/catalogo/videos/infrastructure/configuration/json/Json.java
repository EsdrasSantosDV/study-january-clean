package com.esdras.catalogo.videos.infrastructure.configuration.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;

//UMA OUTRA MANEIRA DE CRIAR CLASES UTILS E SINGLETON
//E POR ENUMS
public enum Json {
    INSTANCE;
    //COM ISSO TEMOS NOSSO SINGLETON

    //ESSE DO PROPRIO SPRING E VAMOS CUSTOMIZAR
    //ESSE JAVA TIME MODULE E PRA SERIALIZAR E DESSERIALIZAR DATAS QUE VIERAM NO JAVA TIME
    //INSTANT, LOCAL DATE
    //O JDK8 E PRA SERIALIZAR E DESSERIALIZAR OPTIONAL
    //O AFTERBURNER E PRA MELHORAR A PERFORMANCE
    private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .dateFormat(new StdDateFormat())
            .featuresToDisable(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                    DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            )
            .modules(new JavaTimeModule(), new Jdk8Module(), afterburnerModule())
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)//PRA SNAKE CASE
            .build();

    //PRECISAMOS EXPORTAS OS METODOS UTILS PRA PODER USAR O MAPPER DE FORMA MAIS SIMPLES
    public static ObjectMapper mapper() {
        return INSTANCE.mapper.copy();
    }

    //VAMOS OFUSCAR AS EXCEPTIONS DELES
    public static String writeValueAsString(final Object obj) {
        return invoke(() -> INSTANCE.mapper.writeValueAsString(obj));
    }

    public static <T> T readValue(final String json, final Class<T> clazz) {
        return invoke(() -> INSTANCE.mapper.readValue(json, clazz));
    }

    private static <T> T invoke(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AfterburnerModule afterburnerModule() {
        var module = new AfterburnerModule();
        // make Afterburner generate bytecode only for public getters/setter and fields
        // without this, Java 9+ complains of "Illegal reflective access"
        //ELE GERA APENAS O BITE CODE PRA OS GETTERS E SETTERS PUBLICOS
        //A PARTIR DO JAVA 9 ELE RECLAMA DE ACESSO REFLEXIVO ILEGAL
        module.setUseValueClassLoader(false);
        return module;
    }

}