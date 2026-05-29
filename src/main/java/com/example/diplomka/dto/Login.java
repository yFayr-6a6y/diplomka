package com.example.diplomka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Объект для авторизации")
public class Login {

    @Schema(description = "логин", minLength = 4, maxLength = 32)
    @Size(min = 4, max = 32)
    private String username;

    @Schema(description = "пароль", minLength = 8, maxLength = 16)
    @Size(min = 8, max = 16)
    private String password;
}