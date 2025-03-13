package com.itschool.library_management.mapper;

import com.itschool.library_management.dto.ReviewDto;
import com.itschool.library_management.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "bookId", source = "book.id")
    ReviewDto toDto(Review review);

    @Mapping(target = "book", ignore = true)
    Review toEntity(ReviewDto reviewDto);
}