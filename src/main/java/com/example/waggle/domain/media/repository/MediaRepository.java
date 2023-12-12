package com.example.waggle.domain.media.repository;

import com.example.waggle.domain.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByBoardId(Long boardId);

    Optional<Media> findByUploadFile(String uploadFile);
}