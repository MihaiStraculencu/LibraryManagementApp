package com.itschool.library_management.service;

import com.itschool.library_management.dto.AuthorDto;
import com.itschool.library_management.entity.Author;
import com.itschool.library_management.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<AuthorDto> getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(this::convertToDto);
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = convertToEntity(authorDto);
        Author savedAuthor = authorRepository.save(author);
        return convertToDto(savedAuthor);
    }

    public Optional<AuthorDto> updateAuthor(Long id, AuthorDto authorDto) {
        if (!authorRepository.existsById(id)) {
            return Optional.empty();
        }
        authorDto.setId(id);
        Author author = convertToEntity(authorDto);
        Author updatedAuthor = authorRepository.save(author);
        return Optional.of(convertToDto(updatedAuthor));
    }

    public boolean deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            return false;
        }
        authorRepository.deleteById(id);
        return true;
    }

    private AuthorDto convertToDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBiography(author.getBiography());
        return dto;
    }

    private Author convertToEntity(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setBiography(dto.getBiography());
        return author;
    }
}