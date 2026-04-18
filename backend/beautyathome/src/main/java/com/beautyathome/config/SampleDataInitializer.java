package com.beautyathome.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.beautyathome.domain.client.Client;
import com.beautyathome.domain.client.port.out.ClientRepositoryPort;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    private final ClientRepositoryPort clientRepository;

    public SampleDataInitializer(ClientRepositoryPort clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) {
        // Inicialización de datos para pruebas. 
        // Se ejecuta automáticamente al arrancar la aplicación.
        if (clientRepository.findAll().isEmpty()) {
            Client client = new Client("1", "Usuario de Prueba", "prueba@example.com");
            clientRepository.save(client);
        }
    }
}