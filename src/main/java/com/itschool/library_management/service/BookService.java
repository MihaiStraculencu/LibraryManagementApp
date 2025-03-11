package com.itschool.library_management.service;

import com.itschool.library_management.dto.BookDto;
import com.itschool.library_management.entity.Author;
import com.itschool.library_management.entity.Book;
import com.itschool.library_management.entity.Genre;
import com.itschool.library_management.repository.AuthorRepository;
import com.itschool.library_management.repository.BookRepository;
import com.itschool.library_management.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDto);
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    public Optional<BookDto> updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            return Optional.empty();
        }
        bookDto.setId(id);
        Book book = convertToEntity(bookDto);
        Book updatedBook = bookRepository.save(book);
        return Optional.of(convertToDto(updatedBook));
    }

    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());

        if (book.getGenre() != null) {
            dto.setGenreId(book.getGenre().getId());
        }

        Set<Long> authorIds = book.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        dto.setAuthorIds(authorIds);

        return dto;
    }

    private Book convertToEntity(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());

        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseGet(() -> null);
            book.setGenre(genre);
        }

        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(authorId -> authorRepository.findById(authorId).orElseGet(() -> null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        }

        return book;
    }
}