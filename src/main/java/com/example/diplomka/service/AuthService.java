package com.example.diplomka.service;

import com.example.diplomka.dto.Register;
import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean register(Register reg) {
        if (userRepository.findByEmail(reg.getUsername()).isPresent()) {
            return false;
        }
        UserEntity user = new UserEntity();
        user.setEmail(reg.getUsername());
        user.setPassword(passwordEncoder.encode(reg.getPassword()));
        user.setFirstName(reg.getFirstName());
        user.setLastName(reg.getLastName());
        user.setPhone(reg.getPhone());

        // Безопасная обработка роли (защита от NullPointerException)
        String role = reg.getRole();
        if (role == null || role.trim().isEmpty()) {
            user.setRole("ROLE_USER"); // Если роль не передали, ставим дефолтную
        } else {
            user.setRole(role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase());
        }

        userRepository.save(user);
        return true;
    }
}