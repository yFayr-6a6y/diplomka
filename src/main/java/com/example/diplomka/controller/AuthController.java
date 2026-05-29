package com.example.diplomka.controller;

import com.example.diplomka.dto.Login;
import com.example.diplomka.dto.Register;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@Tag(name = "Авторизация и Регистрация")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> register(@RequestBody Register register) {
        return ResponseEntity.status(201).build();
    }
}