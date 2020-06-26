package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media, Integer> {}
