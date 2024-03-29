package com.example.baekboom.backend.controller;

import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.dto.memberRankDto;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.service.TeamSetService;
import com.example.baekboom.backend.service.LoginService;
import com.example.baekboom.backend.crawling.tierProblemcrawling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class TeamSetController {
    private TeamSetService teamSetService;
    private LoginService loginService;
    private tierProblemcrawling tierProblemCrawling;

    @Autowired
    public TeamSetController(TeamSetService teamSetService, LoginService loginService, tierProblemcrawling tierProblemcrawling){
        this.teamSetService = teamSetService;
        this.tierProblemCrawling = tierProblemcrawling;
        this.loginService = loginService;
    }


    @GetMapping("/{any}")
    public List<TeamEntity> gettAllTeams() {
        return teamSetService.getAllTeams();
    }


    // 해당 코드를 통해서 rank와 id, score 그리고 폭탄 위치인 사람이 나타난다.
    @GetMapping("/rank")
    public List<memberRankDto> team_rank(@RequestParam String inputId){
        LoginService.Tuple<memberDto, List<memberRankDto>> rank =  loginService.login(inputId);
        return rank.getLst_rank();
    }

//    @GetMapping("/")
//    public List<TeamEntity> gettAllTeams() {
//        return teamSetService.getAllTeams();
//    }

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
    @PostMapping("/random-bomb-pos")
    public Map<Long, String> generateRandomBombPos(@RequestParam String team_code) {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(10);
        int cnt = 3;
        Long level = 3L;
        Map<Long, String> problem = tierProblemCrawling.recommend_problems(level, team_code, cnt);
        List<Long> problems = problem.keySet().stream().toList();
        teamSetService.generateRandomBombPos(team_code, startTime, endTime);
        teamSetService.run(problems, team_code, level);
        return problem;
    }
}