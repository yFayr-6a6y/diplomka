package com.example.diplomka.service;

import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("---> Секьюрити ищет пользователя: " + username); // Вывод в консоль

        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    System.out.println("---> ОШИБКА: Пользователь " + username + " не найден в БД!");
                    return new UsernameNotFoundException("Пользователь не найден");
                });

        System.out.println("---> УСПЕХ: Найден " + userEntity.getEmail() + ", Роль: " + userEntity.getRole());

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRole())
                .build();
    }
}