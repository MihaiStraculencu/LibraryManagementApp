package com.itschool.library_management.service;

import com.itschool.library_management.dto.BookDto;
import com.itschool.library_management.entity.Author;
import com.itschool.library_management.entity.Book;
import com.itschool.library_management.entity.Genre;
import com.itschool.library_management.entity.Publisher;
import com.itschool.library_management.mapper.BookMapper;
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
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository,
                       PublisherRepository publisherRepository,
                       BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    public BookDto createBook(BookDto bookDto) {
        // First convert using mapper
        Book book = bookMapper.toEntity(bookDto);

        setRelationships(book, bookDto);

        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public Optional<BookDto> updateBook(Long id, BookDto bookDto) {
        if (!bookRepository.existsById(id)) {
            return Optional.empty();
        }

        bookDto.setId(id);


        Book existingBook = bookRepository.findById(id).orElseThrow();

        bookMapper.updateBookFromDto(bookDto, existingBook);

        setRelationships(existingBook, bookDto);

        Book updatedBook = bookRepository.save(existingBook);
        return Optional.of(bookMapper.toDto(updatedBook));
    }

    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void setRelationships(Book book, BookDto dto) {
        // Set genre
        if (dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId()).orElse(null);
            book.setGenre(genre);
        } else {
            book.setGenre(null);
        }

        // Set authors
        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
            Set<Author> authors = dto.getAuthorIds().stream()
                    .map(authorId -> authorRepository.findById(authorId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            book.setAuthors(authors);
        } else {
            book.setAuthors(new HashSet<>());
        }

        // Set publisher
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId()).orElse(null);
            book.setPublisher(publisher);
        } else {
            book.setPublisher(null);
        }
    }
}