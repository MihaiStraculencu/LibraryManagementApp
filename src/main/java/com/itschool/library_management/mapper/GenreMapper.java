package com.itschool.library_management.mapper;

import com.itschool.library_management.dto.GenreDto;
import com.itschool.library_management.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    @Mapping(target = "books", ignore = true)
    Genre toEntity(GenreDto genreDto);

    GenreDto toDto(Genre genre);
}