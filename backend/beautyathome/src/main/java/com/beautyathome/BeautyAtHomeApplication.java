package com.beautyathome; // Faltaba el paquete raÃ­z

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Spring escanearÃ¡ automÃ¡ticamente todo lo que estÃ© bajo com.beautyathome.*
public class BeautyAtHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeautyAtHomeApplication.class, args);
    }
}