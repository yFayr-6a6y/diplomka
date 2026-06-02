package com.example.diplomka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class ImageEntity {
    @Id
    private String id;

    @Lob
    private byte[] bytes;
}