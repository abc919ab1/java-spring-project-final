package com.example.esp32cam_server.controller;

import com.example.esp32cam_server.model.RawPhoto;
import com.example.esp32cam_server.repository.RawPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/raw-photos")
public class RawPhotoController {

    @Autowired
    private RawPhotoRepository repo;

    @GetMapping
    public List<RawPhoto> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<RawPhoto> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RawPhoto create(@RequestBody RawPhoto r) {
        r.setTimestamp(LocalDateTime.now());
        return repo.save(r);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawPhoto> update(@PathVariable Long id,
                                           @RequestBody RawPhoto newData) {
        return repo.findById(id)
                .map(r -> {
                    r.setDescription(newData.getDescription());
                    r.setIsoValue(newData.getIsoValue());
                    return ResponseEntity.ok(repo.save(r));
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