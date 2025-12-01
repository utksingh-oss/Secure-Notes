package com.secure.notes.security.service;

import com.secure.notes.security.entity.Role;
import com.secure.notes.security.entity.User;
import com.secure.notes.security.enums.AppRole;
import com.secure.notes.security.repository.RoleRepository;
import com.secure.notes.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SecurityUserSetupService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String DEFAULT_SIGNUP_METHOD = "email";
    private final List<UserDetail> userDetailList = List.of(
            new UserDetail("user1", "user1@example.com", "password1", AppRole.ROLE_USER),
            new UserDetail("admin", "admin@example.com", "adminPass", AppRole.ROLE_ADMIN)
    );

    @Autowired
    public SecurityUserSetupService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createDefaultUsers() {
        for (UserDetail userDetail : userDetailList) {
            createUserIfNotExists(userDetail);
        }
    }

    private void createUserIfNotExists(UserDetail userDetail) {
        Role userRole = roleRepository.findByRoleName(userDetail.appRole)
                .orElseGet(() -> roleRepository.save(new Role(userDetail.appRole)));
        if (!userRepository.existsByUsername(userDetail.username)) {
            createUser(userDetail, userRole);
        }
    }

    private void createUser(UserDetail userDetail, Role userRole) {
        User user = User.builder()
                .username(userDetail.username)
                .email(userDetail.emailId)
                .password(passwordEncoder.encode(userDetail.password))
                .build();
        populateUserFieldsWithDefaultValues(user);
        user.setRole(userRole);
        userRepository.save(user);
    }

    private void populateUserFieldsWithDefaultValues(User user) {
        user.setAccountNonLocked(false);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        user.setAccountExpiryDate(LocalDate.now().plusYears(1));
        user.setTwoFactorEnabled(false);
        user.setSignUpMethod(DEFAULT_SIGNUP_METHOD);
    }

    private record UserDetail(String username,
                              String emailId,
                              String password,
                              AppRole appRole) {
    }
}
