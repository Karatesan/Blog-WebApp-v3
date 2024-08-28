package com.karatesan.WebAppApi.repositories;

import com.karatesan.WebAppApi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
