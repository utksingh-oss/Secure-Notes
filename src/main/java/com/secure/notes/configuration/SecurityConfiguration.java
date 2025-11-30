package com.secure.notes.configuration;

import com.secure.notes.security.filter.CustomLoggingFilter;
import com.secure.notes.security.filter.RequestValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests -> {
            requests
                    .requestMatchers("/api/admin/**").hasRole("ADMIN") //ROLE is automatically prepended
                    .requestMatchers("/api/public/**").permitAll()
                    .anyRequest().authenticated();
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // -- Adding Custom Filters
        httpSecurity.addFilterBefore(new CustomLoggingFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(new RequestValidationFilter(), CustomLoggingFilter.class);

        httpSecurity.httpBasic(withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder BcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
