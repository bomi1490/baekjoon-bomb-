package com.example.baekboom.backend.repository;

import com.example.baekboom.backend.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface teamRepository extends JpaRepository<TeamEntity, String> {

    @Override
    TeamEntity getReferenceById(String s);
}
