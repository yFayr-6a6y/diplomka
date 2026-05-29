package com.example.diplomka.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ads")
@Data
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String title;
    private Integer price;
    private String description;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity author;
}