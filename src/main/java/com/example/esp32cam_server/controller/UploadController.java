package com.example.esp32cam_server.controller;

import com.example.esp32cam_server.model.Photo;
import com.example.esp32cam_server.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:5173") // allow React frontend
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private PhotoRepository photoRepository;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestBody byte[] imageBytes) {
        try {
            // create folder if needed
            Path folder = Paths.get(uploadPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }

            // generate filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "photo_" + timestamp + ".jpg";
            Path filePath = folder.resolve(filename);

            // save image bytes
            Files.write(filePath, imageBytes);

            // ✅ save record in database
            Photo photo = new Photo(filename, filePath.toString(), LocalDateTime.now());
            photoRepository.save(photo);

            System.out.println("Saved: " + filePath.toAbsolutePath());
            return ResponseEntity.ok("Uploaded and saved: " + filename);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Upload failed: " + e.getMessage());
        }
    }
}
