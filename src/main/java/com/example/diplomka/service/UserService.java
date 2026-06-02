package com.example.diplomka.service;

import com.example.diplomka.dto.NewPassword;
import com.example.diplomka.dto.UpdateUser;
import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public void setPassword(NewPassword newPassword, String email) {
        UserEntity user = findByEmail(email);
        if (passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new RuntimeException("Неверный текущий пароль");
        }
    }

    public UserEntity updateUser(UpdateUser dto, String email) {
        UserEntity user = findByEmail(email);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        return userRepository.save(user);
    }

    public void updateImage(MultipartFile image, String email) {
        UserEntity user = findByEmail(email);
        user.setImage(imageService.saveImage(image));
        userRepository.save(user);
    }
}