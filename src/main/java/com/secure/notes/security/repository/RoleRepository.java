package com.secure.notes.security.repository;

import com.secure.notes.security.entity.Role;
import com.secure.notes.security.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
