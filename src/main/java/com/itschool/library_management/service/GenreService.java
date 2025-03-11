package com.itschool.library_management.service;

import com.itschool.library_management.dto.GenreDto;
import com.itschool.library_management.entity.Genre;
import com.itschool.library_management.repository.GenreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<GenreDto> getGenreById(Long id) {
        return genreRepository.findById(id)
                .map(this::convertToDto);
    }

    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = convertToEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return convertToDto(savedGenre);
    }

    public Optional<GenreDto> updateGenre(Long id, GenreDto genreDto) {
        if (!genreRepository.existsById(id)) {
            return Optional.empty();
        }
        genreDto.setId(id);
        Genre genre = convertToEntity(genreDto);
        Genre updatedGenre = genreRepository.save(genre);
        return Optional.of(convertToDto(updatedGenre));
    }

    public boolean deleteGenre(Long id) {
        if (!genreRepository.existsById(id)) {
            return false;
        }
        genreRepository.deleteById(id);
        return true;
    }

    private GenreDto convertToDto(Genre genre) {
        GenreDto dto = new GenreDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());
        return dto;
    }

    private Genre convertToEntity(GenreDto dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());
        return genre;
    }
}