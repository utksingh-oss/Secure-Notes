package com.secure.notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /*
     * All the HttpRequests need to be authorized
     * httpSecurity.formLogin(withDefaults()); -- removed, enabled by default
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request) -> request.anyRequest().authenticated());
        httpSecurity.httpBasic(withDefaults());
        return httpSecurity.build();
    }
}
