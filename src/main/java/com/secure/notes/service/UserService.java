package com.secure.notes.service;

import com.secure.notes.model.UserDto;
import com.secure.notes.security.entity.User;

import java.util.List;

public interface UserService {
    void updateUserRole(Long userId, String roleName);

    List<User> getAllUsers();

    UserDto getUserById(Long id);
}
