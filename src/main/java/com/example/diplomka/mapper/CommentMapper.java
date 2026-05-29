package com.example.diplomka.mapper;

import com.example.diplomka.dto.Comment;
import com.example.diplomka.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", source = "author.id")
    Comment toDTO(CommentEntity entity);

    @Mapping(target = "author.id", source = "author")
    CommentEntity toEntity(Comment dto);
}