package com.example.esp32cam_server.repository;

import com.example.esp32cam_server.model.RawPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawPhotoRepository extends JpaRepository<RawPhoto, Long> {
}