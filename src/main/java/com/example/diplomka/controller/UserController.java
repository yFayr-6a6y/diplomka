package com.example.diplomka.controller;

import com.example.diplomka.dto.User;
import com.example.diplomka.mapper.UserMapper;
import com.example.diplomka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication auth) {
        return ResponseEntity.ok(userMapper.toDTO(userService.findByEmail(auth.getName())));
    }
}