package com.example.esp32cam_server.repository;

import com.example.esp32cam_server.model.ProcessedPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedPhotoRepository extends JpaRepository<ProcessedPhoto, Long> {
}