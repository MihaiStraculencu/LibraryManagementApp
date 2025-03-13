package com.itschool.library_management.controller;

import com.itschool.library_management.dto.PublisherDto;
import com.itschool.library_management.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<PublisherDto>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDto> getPublisherById(@PathVariable Long id) {
        return publisherService.getPublisherById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherDto publisherDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(publisherService.createPublisher(publisherDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long id, @RequestBody PublisherDto publisherDto) {
        return publisherService.updatePublisher(id, publisherDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        return publisherService.deletePublisher(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}