package com.example.diplomka.mapper;

import com.example.diplomka.dto.Ad;
import com.example.diplomka.entity.AdEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdMapper {
    @Mapping(target = "author", source = "author.id")
    Ad toDTO(AdEntity entity);

    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "description", ignore = true) // Игнорируем, если description в AdEntity отсутствует
    AdEntity toEntity(Ad dto);
}