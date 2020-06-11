package com.internship.tabulaprocessing.media.repository;

import com.internship.tabulaprocessing.media.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {

}