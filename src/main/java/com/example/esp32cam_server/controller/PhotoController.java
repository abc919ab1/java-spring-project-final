package com.example.esp32cam_server.controller;

import com.example.esp32cam_server.dto.PhotoUpdateDTO;
import com.example.esp32cam_server.model.Photo;
import com.example.esp32cam_server.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    // --- READ ALL ---
    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    // --- READ ONE ---
    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable Long id) {
        Optional<Photo> photo = photoRepository.findById(id);
        return photo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        if (photoRepository.existsById(id)) {
            photoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- UPDATE (add/edit description) ---
    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhotoDescription(
            @PathVariable Long id,
            @RequestBody PhotoUpdateDTO dto) {

        Optional<Photo> optionalPhoto = photoRepository.findById(id);
        if (optionalPhoto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Photo photo = optionalPhoto.get();
        photo.setDescription(dto.getDescription());
        photoRepository.save(photo);

        return ResponseEntity.ok(photo);
    }

}
