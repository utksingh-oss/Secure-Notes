package com.secure.notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf ->
                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/auth/public/**") // disabling CSRF protection for the endpoints
        );
        httpSecurity.authorizeHttpRequests(requests -> {
            requests
                    .requestMatchers("/api/admin/**").hasRole("ADMIN") //ROLE is automatically prepended
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/csrf-token").permitAll()
                    .anyRequest().authenticated();
        });
        httpSecurity.httpBasic(withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder BcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
