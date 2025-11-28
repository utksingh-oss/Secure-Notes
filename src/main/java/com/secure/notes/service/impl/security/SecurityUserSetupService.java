package com.secure.notes.service.impl.security;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserSetupService {
    private final UserDetailsManager userDetailsManager;


    @Autowired
    public SecurityUserSetupService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Transactional
    public void createDefaultUsers() {
        if (!userDetailsManager.userExists("user1")) {
            userDetailsManager.createUser(
                    User.withUsername("user1")
                            .password("{noop}password1") //No Encryption
                            .roles("USER")
                            .build()
            );
        }
        if (!userDetailsManager.userExists("admin")) {
            userDetailsManager.createUser(
                    User.withUsername("admin")
                            .password("{noop}adminPass") //No Encryption
                            .roles("ADMIN")
                            .build()
            );
        }
    }
}
