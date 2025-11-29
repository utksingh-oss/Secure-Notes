package com.secure.notes;

import com.secure.notes.security.service.SecurityUserSetupService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class NotesApplication {
    private static final String DB_URL = "DB_URL";
    private static final String DB_USERNAME = "DB_USERNAME";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    public static void main(String[] args) {
        loadEnvironmentVariables();
        SpringApplication.run(NotesApplication.class, args);
    }

    private static void loadEnvironmentVariables() {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty(DB_URL, Objects.requireNonNull(dotenv.get(DB_URL)));
        System.setProperty(DB_USERNAME, Objects.requireNonNull(dotenv.get(DB_USERNAME)));
        System.setProperty(DB_PASSWORD, Objects.requireNonNull(dotenv.get(DB_PASSWORD)));
    }

    @Bean
    public ApplicationRunner initializeUsers(SecurityUserSetupService securityUserSetupService) {
        return args -> securityUserSetupService.createDefaultUsers();
    }

}
