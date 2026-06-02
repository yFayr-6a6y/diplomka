package com.example.diplomka.controller;

import com.example.diplomka.dto.NewPassword;
import com.example.diplomka.dto.UpdateUser;
import com.example.diplomka.dto.User;
import com.example.diplomka.mapper.UserMapper;
import com.example.diplomka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/set_password")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword newPassword, Authentication auth) {
        userService.setPassword(newPassword, auth.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(Authentication auth) {
        return ResponseEntity.ok(userMapper.toDTO(userService.findByEmail(auth.getName())));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser, Authentication auth) {
        userService.updateUser(updateUser, auth.getName());
        return ResponseEntity.ok(updateUser);
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image, Authentication auth) {
        // Заглушка обновления аватарки
        return ResponseEntity.ok().build();
    }
}