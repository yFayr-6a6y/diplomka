package com.example.diplomka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor // Добавит нужный конструктор для (count, results)
@NoArgsConstructor  // Обязательно для корректной работы Spring/JSON
@Schema(description = "Список комментариев")
public class Comments {
    private Integer count;
    private List<Comment> results;
}