package com.example.esp32cam_server.controller;

import com.example.esp32cam_server.model.Photo;
import com.example.esp32cam_server.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class UploadController {

    @Autowired
    private PhotoRepository photoRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody byte[] imageBytes) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "photo_" + timestamp + ".jpg";

            Path folder = Paths.get("uploads");
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            Path filePath = folder.resolve(filename);
            Files.write(filePath, imageBytes);

            // save metadata in database
            Photo photo = new Photo(filename, filePath.toString(), LocalDateTime.now());
            photoRepository.save(photo);

            System.out.println("Saved: " + filePath.toAbsolutePath());
            return ResponseEntity.ok("Uploaded and saved: " + filename);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
        }
    }

    // NEW — list all saved photos
    @GetMapping("upload/photos")
    public ResponseEntity<?> listPhotos() {
        return ResponseEntity.ok(photoRepository.findAll());
    }
}
