package com.example.diplomka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrUpdateComment {
    @Size(min = 8, max = 64)
    private String text;
}