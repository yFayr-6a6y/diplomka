package com.example.diplomka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    private String title;

    private Integer price;

    private String description;

    // Ссылка на картинку обычно хранится как путь к файлу
    private String image;

    // Связь с автором объявления (много объявлений - один пользователь)
    // FetchType.LAZY обязателен по критериям оценки
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;
}