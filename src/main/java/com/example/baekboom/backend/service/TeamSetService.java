package com.example.baekboom.backend.service;

import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.ProblemEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.repository.teamRepository;
import com.example.baekboom.backend.repository.memberRepository;
import com.example.baekboom.backend.repository.problemRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamSetService extends Thread{
    private final teamRepository teamRepository;
    private final memberRepository memberRepository;
    private final problemRepository problemRepository;
    private LocalDateTime globalBombStartTime;

    public void setGlobalBombStartTime(LocalDateTime time){
        this.globalBombStartTime = time;
    }

    public LocalDateTime getGlobalBombStartTime(){
        return this.globalBombStartTime;
    }

    public TeamSetService(memberRepository memberRepository, teamRepository teamRepository, problemRepository problemRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.problemRepository = problemRepository;
    }

    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }

    // findById를 통해 기본키가 team_code인 멤버들을 반환.
    public TeamEntity getTeamById(String team_code) {
        return teamRepository.findById(team_code).orElse(null);
    }

    // 특정 팀 코드에 대해, team_code를 인자로 받고 List<MemberEntity> 형태로 반환.
    public List<MemberEntity> getMembersByTeamCode(String team_code) {
        return memberRepository.findAllByTeam_Teamcode(team_code);
    }

    // team_code를 인자로 받아 멤버 리스트 생성.
    public void generateRandomBombPos(String team_code, int hours) {
        TeamEntity team = getTeamById(team_code);
        List<MemberEntity> members = getMembersByTeamCode(team_code);
        int memberCount = members.size();
        int randomIndex = (int) (Math.random() * members.size());
        // 멤버 리스트를 쭉 돌며 랜덤 생성된 인덱스와 일치하면 Bomb_yn을 true, bomb_pos는 인덱스 + 1로 바꿈.

        for (int i = 0; i < memberCount; i++) {
            MemberEntity memberEntity = members.get(randomIndex);
            if (i == randomIndex) {
                memberEntity.setBomb_yn(true);
                team.setBomb_pos(memberEntity.getUser_id());
                setGlobalBombStartTime(LocalDateTime.now().plusHours(hours));
            } else {
                memberEntity.setBomb_yn(false);
            }
            teamRepository.save(team);

        }
    }

    // 24시간 내에 event_time이 들어오지 못한다면 점수 10점 감점.
    // 일단은 설정 문제는 아니고 그냥 문제를 풀었는지에 대한 여부만 체크
    public void checkAndReduceScore(String team_code) {
        MemberEntity bombMember = memberRepository.findByBombyn(true);
        List<ProblemEntity> problems = problemRepository.findByUser_UseridAndEventTimeBetween(bombMember.getUser_id(), globalBombStartTime, LocalDateTime.now());
        boolean found = false;
        for (ProblemEntity problem : problems) {
            LocalDateTime eventTime = problem.getEvent_time();
            LocalDateTime now = LocalDateTime.now();
            if (eventTime.isAfter(now.minusDays(1)) && eventTime.isBefore(now)) {
                found = true;
                break;
            }
        }
        if (!found) {
            long score = bombMember.getScore();
            bombMember.setScore(score - 10);
        }
    }

    public void run(){
        while(true){
            if (LocalDateTime.now().isAfter(getGlobalBombStartTime())){
                break;
            }
            //crawling
        }
        //checkAndReduceScore
    }
}