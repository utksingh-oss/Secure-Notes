package com.secure.notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers("/v1/hello/about").permitAll()
                        .requestMatchers("/v1/public/**").permitAll() // Any endpoint starting with public
                        .requestMatchers("/v1/admin/**").denyAll() // When some endpoints are under maintenance
                        .anyRequest().authenticated()
        );
        httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity.httpBasic(withDefaults());
        return httpSecurity.build();
    }
}
