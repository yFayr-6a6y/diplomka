package com.example.diplomka.service;

import com.example.diplomka.dto.Ads;
import com.example.diplomka.mapper.AdMapper;
import com.example.diplomka.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdsService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    public Ads getAllAds() {
        // Логика получения всех объявлений из БД
        // Конвертируем List<AdEntity> в List<Ad> через adMapper
        return new Ads(); // Здесь будет твоя реализация
    }
}