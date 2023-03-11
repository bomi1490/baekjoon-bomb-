package com.example.baekboom.backend.controller;

import com.example.baekboom.backend.Constant.Tier;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.service.TeamSetService;
import com.example.baekboom.backend.service.LoginService;
import com.example.baekboom.backend.dto.memberRankDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TeamSetController {
    @Autowired
    private TeamSetService teamSetService;
    private LoginService loginService;


    @GetMapping("/{any}")
    public List<TeamEntity> gettAllTeams() {
        return teamSetService.getAllTeams();
    }


    // 해당 코드를 통해서 rank와 id, score 그리고 폭탄 위치인 사람이 나타난다.
    @GetMapping("/rank")
    public List<memberRankDto> team_rank(String inputId){
        LoginService.Tuple<memberDto, List<memberRankDto>> rank =  loginService.login(inputId);
        return rank.getLst_rank();
    }



//    @GetMapping("/")
//    public List<TeamEntity> gettAllTeams() {
//        return teamSetService.getAllTeams();
//    }

    // 팀 코드를 적으면 getTeamById 메소드 실행.
//    @GetMapping("/{team_code}")
//    public TeamEntity getTeamById(@PathVariable String team_code){
//        return teamSetService.getTeamById(team_code);
//    }
//
//    @GetMapping("/{team_code}/members")
//    public List<MemberEntity> getMembersByTeamCode(@PathVariable String team_code){
//        return teamSetService.getMembersByTeamCode(team_code);
//    }
//
//    // 팀 코드를 url을 통해 입력받고, service에서 generateRandomBombPos 메소드 실행.
//    @PostMapping("/{team_code}/random-bomb-pos")
//    public void generateRandomBombPos(@PathVariable String team_code, int hours) {
//        teamSetService.generateRandomBombPos(team_code, hours);
//    }
//
//    //

//    @GetMapping("/{team_code}/check-score")
//    public ResponseEntity<Void> checkScore(@PathVariable String team_code){
//        teamSetService.checkAndReduceScore(team_code);
//        return ResponseEntity.ok().build();
//    }
}