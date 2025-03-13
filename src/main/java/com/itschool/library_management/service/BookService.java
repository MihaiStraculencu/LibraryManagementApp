package com.itschool.library_management.service;

import com.itschool.library_management.dto.BookDto;
import com.itschool.library_management.entity.Author;
import com.itschool.library_management.entity.Book;
import com.itschool.library_management.entity.Genre;
import com.itschool.library_management.entity.Publisher;
import com.itschool.library_management.repository.AuthorRepository;
import com.itschool.library_management.repository.BookRepository;
import com.itschool.library_management.repository.GenreRepository;
import com.itschool.library_management.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
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

        if (book.getPublisher() != null) {
            dto.setPublisherId(book.getPublisher().getId());
        }

        Set<Long> authorIds = book.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toSet());
        dto.setAuthorIds(authorIds);

        return dto;
    }

    @Transactional
    public Book convertToEntity(BookDto dto) {
        Book book;

        // daca update existing book, fetch pe primul
        if (dto.getId() != null) {
            book = bookRepository.findById(dto.getId())
                    .orElseGet(Book::new);
        } else {
            book = new Book();
        }

        // Update la properties
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());

        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElse(null);
            book.setGenre(genre);
        } else {
            book.setGenre(null);
        }

        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(authorId -> authorRepository.findById(authorId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        } else {
            book.setAuthors(new HashSet<>());
        }

        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseGet(() -> null);
            book.setPublisher(publisher);
        }

        return book;
    }

}