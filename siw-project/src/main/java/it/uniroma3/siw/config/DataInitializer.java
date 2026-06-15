package it.uniroma3.siw.config;

import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UtenteService utenteService;

    @Override
    public void run(String... args) {
        if (!utenteService.emailEsiste("admin@bookshop.it")) {
            utenteService.registraAdmin("Admin", "admin@bookshop.it", "admin123");
            System.out.println("=================================================");
            System.out.println("  Admin creato:");
            System.out.println("    email:    admin@bookshop.it");
            System.out.println("    password: admin123");
            System.out.println("  CAMBIA LA PASSWORD IN PRODUZIONE!");
            System.out.println("=================================================");
        }
    }
}