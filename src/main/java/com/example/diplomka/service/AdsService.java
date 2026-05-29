package com.example.diplomka.service;

import com.example.diplomka.entity.AdEntity;
import com.example.diplomka.repository.AdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdsService {
    private final AdRepository adRepository;

    public void removeAd(Integer id, Authentication authentication) {
        AdEntity ad = adRepository.findById(id).orElseThrow();
        if (canEdit(ad, authentication)) {
            adRepository.delete(ad);
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    private boolean canEdit(AdEntity ad, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().contains("ROLE_ADMIN"));
        return ad.getAuthor().getEmail().equals(authentication.getName()) || isAdmin;
    }
}