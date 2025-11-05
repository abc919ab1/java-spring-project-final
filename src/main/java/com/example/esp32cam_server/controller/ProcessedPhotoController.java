package com.example.esp32cam_server.controller;

import com.example.esp32cam_server.model.ProcessedPhoto;
import com.example.esp32cam_server.repository.ProcessedPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/processed-photos")
public class ProcessedPhotoController {

    @Autowired
    private ProcessedPhotoRepository repo;

    @GetMapping
    public List<ProcessedPhoto> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessedPhoto> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProcessedPhoto create(@RequestBody ProcessedPhoto p) {
        p.setTimestamp(LocalDateTime.now());
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessedPhoto> update(@PathVariable Long id,
                                                 @RequestBody ProcessedPhoto newData) {
        return repo.findById(id)
                .map(p -> {
                    p.setDescription(newData.getDescription());
                    p.setFilterName(newData.getFilterName());
                    return ResponseEntity.ok(repo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}