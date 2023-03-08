package com.example.baekboom.backend.controller;

import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.service.TeamSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamSetController {
    @Autowired
    private TeamSetService teamSetService;

    @GetMapping("/")
    public List<TeamEntity> gettAllTeams() {
        return teamSetService.getAllTeams();
    }

    // 팀 코드를 적으면 getTeamById 메소드 실행.
    @GetMapping("/{team_code}")
    public TeamEntity getTeamById(@PathVariable String team_code){
        return teamSetService.getTeamById(team_code);
    }

    @GetMapping("/{team_code}/members")
    public List<MemberEntity> getMembersByTeamCode(@PathVariable String team_code){
        return teamSetService.getMembersByTeamCode(team_code);
    }

    // 팀 코드를 url을 통해 입력받고, service에서 generateRandomBombPos 메소드 실행.
    @PostMapping("/{team_code}/random-bomb-pos")
    public void generateRandomBombPos(@PathVariable String team_code, int hours) {
        teamSetService.generateRandomBombPos(team_code, hours);
    }

    //
    @GetMapping("/{team_code}/check-score")
    public ResponseEntity<Void> checkScore(@PathVariable String team_code){
        teamSetService.checkAndReduceScore(team_code);
        return ResponseEntity.ok().build();
    }
}