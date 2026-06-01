package com.example.diplomka.controller;

import com.example.diplomka.dto.Ad;
import com.example.diplomka.dto.Ads;
import com.example.diplomka.dto.CreateOrUpdateAd;
import com.example.diplomka.mapper.AdMapper;
import com.example.diplomka.service.AdsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;
    private final AdMapper adMapper;

    @GetMapping
    public ResponseEntity<Ads> getAllAds() {
        var adsList = adsService.findAll().stream().map(adMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(new Ads(adsList.size(), adsList));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Ad> addAd(@RequestPart("properties") CreateOrUpdateAd properties,
                                    @RequestPart("image") MultipartFile image,
                                    Authentication auth) {
        return ResponseEntity.status(201).body(adMapper.toDTO(adsService.addAd(properties, image, auth)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id, Authentication auth) {
        adsService.removeAd(id, auth);
        return ResponseEntity.noContent().build();
    }
}