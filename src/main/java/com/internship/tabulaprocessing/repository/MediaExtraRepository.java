package com.internship.tabulaprocessing.repository;

import com.internship.tabulaprocessing.entity.MediaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaExtraRepository extends JpaRepository<MediaExtra, Integer> {
}