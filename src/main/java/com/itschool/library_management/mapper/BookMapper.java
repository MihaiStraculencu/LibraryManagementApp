package com.itschool.library_management.mapper;

import com.itschool.library_management.dto.BookDto;
import com.itschool.library_management.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, GenreMapper.class, PublisherMapper.class})
public interface BookMapper {

    @Mapping(target = "authorIds", ignore = true)
    @Mapping(target = "genreId", source = "genre.id")
    @Mapping(target = "publisherId", source = "publisher.id")
    BookDto toDto(Book book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Book toEntity(BookDto bookDto);

    @AfterMapping
    default void mapAuthorIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getAuthors() != null) {
            bookDto.setAuthorIds(book.getAuthors().stream()
                    .map(author -> author.getId())
                    .collect(java.util.stream.Collectors.toSet()));
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genre", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    void updateBookFromDto(BookDto dto, @MappingTarget Book book);
}