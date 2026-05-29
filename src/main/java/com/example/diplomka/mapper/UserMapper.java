package com.example.diplomka.mapper;

import com.example.diplomka.dto.User;
import com.example.diplomka.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDTO(UserEntity entity);
    UserEntity toEntity(User dto);
}