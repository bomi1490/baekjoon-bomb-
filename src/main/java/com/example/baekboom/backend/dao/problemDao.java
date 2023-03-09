package com.example.baekboom.backend.dao;


import com.example.baekboom.backend.dto.problemDto;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.ProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.baekboom.backend.repository.problemRepository;
import com.example.baekboom.backend.repository.memberRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class problemDao {

    private final problemRepository problemRepository;
    private final memberRepository memberRepository;

    @Autowired
    public problemDao(problemRepository problemRepository, memberRepository memberRepository) {
        this.problemRepository = problemRepository;
        this.memberRepository = memberRepository;
    }
    // 문제를 맞추면 넣음

    public void saveproblem(problemDto problem) {
        MemberEntity member = memberRepository.getReferenceById(problem.getUser_id());
        ProblemEntity problemEntity = new ProblemEntity();
        problemEntity.setProblem(problem.getProblem());
        problemEntity.setUser(member);
        problemEntity.setLevel(problem.getLevel());

        problemRepository.save(problemEntity);
    }

    public List<Long> get_problems_with_level(String user_id, Long level) {
        //List<ProblemEntity> problems = problemRepository.findAllByLevelAndUser_UserId(level, user_id);
        List<Long> return_problems = new ArrayList<>();
        //problems.forEach(item -> return_problems.add(item.getLevel()));
        return return_problems;
    }

    public Boolean problem_exist(String user_id, Long problem) {
        if (problemRepository.findByUser_UseridAndProblem(user_id, problem) == null) {
            return false;
        } else {
            return true;
        }
    }
}
