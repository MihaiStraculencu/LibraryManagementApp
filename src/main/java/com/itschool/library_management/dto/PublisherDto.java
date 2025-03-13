package com.itschool.library_management.dto;

public class PublisherDto {
    private Long id;
    private String name;
    private String address;
    private String website;
    private String email;

    // Constructors
    public PublisherDto() {
    }

    public PublisherDto(Long id, String name, String address, String website, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}