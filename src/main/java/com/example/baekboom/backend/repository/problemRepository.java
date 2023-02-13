package com.example.baekboom.backend.repository;

import com.example.baekboom.backend.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface problemRepository extends JpaRepository<ProblemEntity, Long> {


    List<ProblemEntity> findAllByLevelAndUser(Long level, String user_id); // 크롤링할 때 사용하여 동일한 level을 제거

}
