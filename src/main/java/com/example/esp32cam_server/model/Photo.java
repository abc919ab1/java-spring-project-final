package com.example.esp32cam_server.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PHOTO")
public class Photo extends Media {


    public Photo() {
        super();
    }

    public Photo(String filename, String filePath, java.time.LocalDateTime timestamp) {
        super(filename, filePath, timestamp);
    }


}
