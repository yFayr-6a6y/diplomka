package com.example.diplomka.service;

import com.example.diplomka.entity.ImageEntity;
import com.example.diplomka.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public String saveImage(MultipartFile file) {
        ImageEntity image = new ImageEntity();
        image.setId(UUID.randomUUID().toString());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения картинки");
        }
        imageRepository.save(image);
        return "/images/" + image.getId();
    }

    public byte[] getImage(String id) {
        return imageRepository.findById(id).orElseThrow().getBytes();
    }
}