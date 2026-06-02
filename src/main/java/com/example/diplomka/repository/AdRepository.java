package com.example.diplomka.repository;

import com.example.diplomka.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdRepository extends JpaRepository<AdEntity, Integer> {
    List<AdEntity> findAllByAuthor_Email(String email); // Поиск объявлений конкретного юзера
}