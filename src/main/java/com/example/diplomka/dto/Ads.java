package com.example.diplomka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor; // Добавь этот импорт
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor // Добавь эту аннотацию
@Schema(description = "Список объявлений")
public class Ads {
    private Integer count;
    private List<Ad> results;
}