package com.example.diplomka.controller;

import com.example.diplomka.dto.Ads;
import com.example.diplomka.dto.Ad;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления")
public class AdsController {

    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Ads> getAllAds() {
        return ResponseEntity.ok(new Ads()); // Заглушка
    }

    @PostMapping
    @Operation(summary = "Добавление объявления")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<Ad> addAd(@RequestParam String properties, @RequestParam String image) {
        return ResponseEntity.status(201).body(new Ad()); // Заглушка
    }


}