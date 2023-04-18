package com.example.demo.repository;

import com.example.demo.constant.RemunerationLevel;
import com.example.demo.entity.RemunerationLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemunerationLevelRepository extends JpaRepository<RemunerationLevelEntity, Long> {
    public RemunerationLevelEntity findByName(RemunerationLevel remunerationLevel);
}
