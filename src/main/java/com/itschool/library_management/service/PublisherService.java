package com.itschool.library_management.service;

import com.itschool.library_management.dto.PublisherDto;
import com.itschool.library_management.entity.Publisher;
import com.itschool.library_management.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PublisherDto> getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .map(this::convertToDto);
    }

    public PublisherDto createPublisher(PublisherDto publisherDto) {
        Publisher publisher = convertToEntity(publisherDto);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return convertToDto(savedPublisher);
    }

    public Optional<PublisherDto> updatePublisher(Long id, PublisherDto publisherDto) {
        if (!publisherRepository.existsById(id)) {
            return Optional.empty();
        }
        publisherDto.setId(id);
        Publisher publisher = convertToEntity(publisherDto);
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return Optional.of(convertToDto(updatedPublisher));
    }

    public boolean deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            return false;
        }
        publisherRepository.deleteById(id);
        return true;
    }

    private PublisherDto convertToDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setWebsite(publisher.getWebsite());
        dto.setEmail(publisher.getEmail());
        return dto;
    }

    private Publisher convertToEntity(PublisherDto dto) {
        Publisher publisher = new Publisher();
        publisher.setId(dto.getId());
        publisher.setName(dto.getName());
        publisher.setAddress(dto.getAddress());
        publisher.setWebsite(dto.getWebsite());
        publisher.setEmail(dto.getEmail());
        return publisher;
    }
}