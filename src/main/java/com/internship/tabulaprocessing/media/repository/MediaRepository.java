package com.internship.tabulaprocessing.media.repository;


import com.internship.tabulaprocessing.media.model.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {
    Page<Media> findAll(Pageable pageable);
}