package com.esdras.catalogo.videos.infrastructure;

import com.esdras.catalogo.videos.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Esssa configuração e super importante para o spring boot funcionar
// Essa classe é a classe principal QUE INICIA A APLICAÇÃO
// SEM ELA NÃO FUNCIONA
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(WebServerConfig.class, args);
    }
}