package com.example.diplomka.repository;

import com.example.diplomka.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, String> {
}