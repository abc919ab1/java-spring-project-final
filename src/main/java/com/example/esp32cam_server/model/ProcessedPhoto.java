package com.example.esp32cam_server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PROCESSED")
public class ProcessedPhoto extends Media {

    private String filterName;

    public ProcessedPhoto() {}

    public ProcessedPhoto(String filename, String filePath, java.time.LocalDateTime timestamp, String filterName) {
        super(filename, filePath, timestamp);
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}