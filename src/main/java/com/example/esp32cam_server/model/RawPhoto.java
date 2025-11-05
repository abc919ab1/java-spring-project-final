package com.example.esp32cam_server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RAW")
public class RawPhoto extends Media {

    private int isoValue;   // dummy attribute

    public RawPhoto() {}

    public RawPhoto(String filename, String filePath, java.time.LocalDateTime timestamp, int isoValue) {
        super(filename, filePath, timestamp);
        this.isoValue = isoValue;
    }

    public int getIsoValue() {
        return isoValue;
    }

    public void setIsoValue(int isoValue) {
        this.isoValue = isoValue;
    }
}