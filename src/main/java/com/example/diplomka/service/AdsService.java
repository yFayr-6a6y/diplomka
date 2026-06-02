package com.example.diplomka.service;

import com.example.diplomka.dto.CreateOrUpdateAd;
import com.example.diplomka.dto.ExtendedAd;
import com.example.diplomka.entity.AdEntity;
import com.example.diplomka.entity.UserEntity;
import com.example.diplomka.repository.AdRepository;
import com.example.diplomka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdsService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public List<AdEntity> findAll() { return adRepository.findAll(); }

    public List<AdEntity> getAdsMe(Authentication auth) {
        return adRepository.findAllByAuthor_Email(auth.getName());
    }

    public ExtendedAd getAdById(Integer id) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        ExtendedAd dto = new ExtendedAd();
        dto.setPk(ad.getPk());
        dto.setTitle(ad.getTitle());
        dto.setPrice(ad.getPrice());
        dto.setDescription(ad.getDescription());
        dto.setImage(ad.getImage());
        dto.setAuthorFirstName(ad.getAuthor().getFirstName());
        dto.setAuthorLastName(ad.getAuthor().getLastName());
        dto.setEmail(ad.getAuthor().getEmail());
        dto.setPhone(ad.getAuthor().getPhone());
        return dto;
    }

    public AdEntity addAd(CreateOrUpdateAd properties, MultipartFile image, Authentication auth) {
        UserEntity author = userRepository.findByEmail(auth.getName()).orElseThrow();
        AdEntity ad = new AdEntity();
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        ad.setAuthor(author);
        ad.setImage(imageService.saveImage(image)); // Сохраняем картинку
        return adRepository.save(ad);
    }

    public AdEntity updateAd(Integer id, CreateOrUpdateAd body, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        checkPermissions(ad.getAuthor().getEmail(), auth);
        ad.setTitle(body.getTitle());
        ad.setPrice(body.getPrice());
        ad.setDescription(body.getDescription());
        return adRepository.save(ad);
    }

    public byte[] updateImage(Integer id, MultipartFile image, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        checkPermissions(ad.getAuthor().getEmail(), auth);
        ad.setImage(imageService.saveImage(image));
        adRepository.save(ad);
        return imageService.getImage(ad.getImage().replace("/images/", ""));
    }

    public void removeAd(Integer id, Authentication auth) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        checkPermissions(ad.getAuthor().getEmail(), auth);
        adRepository.delete(ad);
    }

    private void checkPermissions(String authorEmail, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().contains("ROLE_ADMIN"));
        if (!authorEmail.equals(auth.getName()) && !isAdmin) {
            throw new AccessDeniedException("Нет прав");
        }
    }
}