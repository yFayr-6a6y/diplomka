package com.example.diplomka.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class CreateOrUpdateAd {
    @Size(min = 4, max = 32)
    private String title;
    private Integer price;
    @Size(min = 8, max = 64)
    private String description;
}