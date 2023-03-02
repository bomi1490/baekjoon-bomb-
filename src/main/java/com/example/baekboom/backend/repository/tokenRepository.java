package com.example.baekboom.backend.repository;

import com.example.baekboom.backend.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

public interface tokenRepository extends JpaRepository<TokenEntity, String> {

    @Override
    TokenEntity getReferenceById(String s);

    List<TokenEntity> findAllByTeam(String Team);
}
