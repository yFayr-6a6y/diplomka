package com.example.diplomka.service;

import com.example.diplomka.dto.CreateOrUpdateAd;
import com.example.diplomka.entity.AdEntity;
import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.AdRepository;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public List<AdEntity> findAll() {
        return adRepository.findAll();
    }

    public AdEntity addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication auth) {
        UserEntity author = userRepository.findByEmail(auth.getName()).orElseThrow();
        AdEntity ad = new AdEntity();
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        ad.setAuthor(author);
        return adRepository.save(ad);
    }

    public void removeAd(Integer id, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        // Здесь можно добавить проверку прав, как мы обсуждали
        adRepository.delete(ad);
    }
}