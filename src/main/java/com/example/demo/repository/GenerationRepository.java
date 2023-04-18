package com.example.demo.repository;

import com.example.demo.entity.GenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<GenerationEntity, Long> {
}
