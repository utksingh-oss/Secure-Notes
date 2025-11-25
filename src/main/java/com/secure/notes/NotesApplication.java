package com.secure.notes;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class NotesApplication {
    private static final String DB_URL = "DB_URL";
    private static final String DB_USERNAME = "DB_USERNAME";
    private static final String DB_PASSWORD = "DB_PASSWORD";

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty(DB_URL, Objects.requireNonNull(dotenv.get(DB_URL)));
        System.setProperty(DB_USERNAME, Objects.requireNonNull(dotenv.get(DB_USERNAME)));
        System.setProperty(DB_PASSWORD, Objects.requireNonNull(dotenv.get(DB_PASSWORD)));
        SpringApplication.run(NotesApplication.class, args);
    }

}
