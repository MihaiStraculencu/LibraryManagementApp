package com.itschool.library_management.mapper;

import com.itschool.library_management.dto.PublisherDto;
import com.itschool.library_management.entity.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    @Mapping(target = "books", ignore = true)
    Publisher toEntity(PublisherDto publisherDto);

    PublisherDto toDto(Publisher publisher);
}