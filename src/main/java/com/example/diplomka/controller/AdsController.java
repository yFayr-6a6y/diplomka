package com.example.diplomka.controller;

import com.example.diplomka.dto.Ad;
import com.example.diplomka.dto.Ads;
import com.example.diplomka.dto.CreateOrUpdateAd;
import com.example.diplomka.dto.ExtendedAd;
import com.example.diplomka.mapper.AdMapper;
import com.example.diplomka.service.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;
    private final AdMapper adMapper;

    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        var list = adsService.findAll().stream().map(adMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new Ads(list.size(), list));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> addAd(@RequestPart("properties") String propertiesString,
                                    @RequestPart("image") MultipartFile image,
                                    Authentication auth) {
        try {
            // Ручная конвертация строки в объект (обход бага Swagger)
            ObjectMapper objectMapper = new ObjectMapper();
            CreateOrUpdateAd properties = objectMapper.readValue(propertiesString, CreateOrUpdateAd.class);

            return ResponseEntity.status(201).body(adMapper.toDTO(adsService.addAd(properties, image, auth)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build(); // Вернет 400, если JSON кривой
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAdById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id, Authentication auth) {
        adsService.removeAd(id, auth);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ad> updateAds(@PathVariable Integer id, @RequestBody CreateOrUpdateAd body, Authentication auth) {
        return ResponseEntity.ok(adMapper.toDTO(adsService.updateAd(id, body, auth)));
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> getAdsMe(Authentication auth) {
        var list = adsService.getAdsMe(auth).stream().map(adMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new Ads(list.size(), list));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id, @RequestPart("image") MultipartFile image, Authentication auth) {
        // Заглушка возврата картинки для фронта (в реале тут сохранение)
        return ResponseEntity.ok().build();
    }
}