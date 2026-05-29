package com.example.diplomka.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Список комментариев")
public class Comments {
    private Integer count;
    private List<Comment> results;
}