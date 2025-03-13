package com.itschool.library_management.mapper;

import com.itschool.library_management.dto.AuthorDto;
import com.itschool.library_management.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorDto authorDto);

    AuthorDto toDto(Author author);
}