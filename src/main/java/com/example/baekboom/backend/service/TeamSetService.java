package com.example.baekboom.backend.service;

import com.example.baekboom.backend.crawling.ProblemSolvercrawling;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.ProblemEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.repository.teamRepository;
import com.example.baekboom.backend.repository.memberRepository;
import com.example.baekboom.backend.repository.problemRepository;
import lombok.Getter;
import lombok.Setter;
import com.example.baekboom.backend.Constant.Tier;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Setter
public class TeamSetService extends Thread{
    private final teamRepository teamRepository;
    private final memberRepository memberRepository;
    private final problemRepository problemRepository;
    private LocalDateTime globalBombLimitTime;
    private LocalDateTime globalBombStartTime;
    private List<String> teammembers;
    private final ProblemSolvercrawling problemSolvercrawling;

    public void setGlobalBombLimitTime(LocalDateTime time){
        this.globalBombLimitTime = time;
    }

    public void setGlobalBombStartTime(LocalDateTime time){
        this.globalBombStartTime = time;
    }

    public LocalDateTime getGlobalBombLimitTime(){
        return this.globalBombLimitTime;
    }

    public LocalDateTime getGlobalBombStartTime(){
        return this.globalBombStartTime;
    }

    public TeamSetService(memberRepository memberRepository, teamRepository teamRepository, problemRepository problemRepository, ProblemSolvercrawling problemSolvercrawling) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
        this.problemRepository = problemRepository;
        this.problemSolvercrawling = problemSolvercrawling;
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
    public void generateRandomBombPos(String team_code, LocalDateTime startTime, LocalDateTime endTime) {
        TeamEntity team = getTeamById(team_code);
        List<MemberEntity> members = getMembersByTeamCode(team_code);
        List<String> member_names = new ArrayList<>();
        members.forEach(item -> member_names.add(item.getUserid()));
        setTeammembers(member_names);
        Duration hours =  Duration.between(startTime.toLocalTime(), endTime.toLocalTime());

        int memberCount = members.size();
        int randomIndex = (int) (Math.random() * members.size());
        // 멤버 리스트를 쭉 돌며 랜덤 생성된 인덱스와 일치하면 Bomb_yn을 true, bomb_pos는 인덱스 + 1로 바꿈.

        for (int i = 0; i < memberCount; i++) {
            MemberEntity memberEntity = members.get(randomIndex);
            if (i == randomIndex) {
                memberEntity.setBombyn(true);
                team.setBomb_pos(memberEntity.getUserid());
                setGlobalBombStartTime(LocalDateTime.now());
                setGlobalBombLimitTime(LocalDateTime.now().plusHours(hours.toHours()));
            } else {
                memberEntity.setBombyn(false);
            }
            teamRepository.save(team);
        }
    }

    // 24시간 내에 event_time이 들어오지 못한다면 점수 10점 감점.
    // 일단은 설정 문제는 아니고 그냥 문제를 풀었는지에 대한 여부만 체크
    public void checkAndReduceScore(String team_code, Boolean flag) {
        String user_id = null;
        List<MemberEntity> bombMember = memberRepository.findAllByTeam_Teamcode(team_code);
        for (MemberEntity member : bombMember) {
            user_id = member.getUserid();
            List<ProblemEntity> problems = problemRepository.findByUser_UseridAndEventtimeBetween(user_id, globalBombStartTime, globalBombLimitTime);
            int problemCount = problems.size();
            if (problemCount >= 1) {
                member.setScore(member.getScore() + 5L * problemCount);
            }
            if (member.getBombyn()) {
                long score = member.getScore();
                if (flag.equals(Boolean.TRUE)) {
                    member.setScore(score - 10);
                }
                member.setBombyn(false);
            }
            memberRepository.save(member);

        }
    }

    // 문제를 풀었는지 체크 후 boolean 타입을 반환하는 메소드.
    public boolean check(String team_code, LocalDateTime localDateTime) {
        String user_id = null;
        List<MemberEntity> bombMember = memberRepository.findAllByTeam_Teamcode(team_code);
        for (MemberEntity member : bombMember) {
            if (member.getBombyn()) {
                user_id = member.getUserid();
            }
            List<ProblemEntity> problems = problemRepository.findByUser_UseridAndEventtimeBetween(user_id, localDateTime, LocalDateTime.now());
            int problemCount = problems.size();
            if (problemCount >= 1){
                member.setBombyn(false);
                return true;
            }
        }
        return false;
    }

    // 팀을 받게 되는 경로
    // 컨트롤러에서 문제를 crawling해서 가져오기
    public void run(List<Long> problems, String team_code, Long level){
        List<String> members = getTeammembers();
        int totalProblemCount = problems.size() * members.size();
        LocalDateTime localDateTime = LocalDateTime.now();
        while(true){
            if (totalProblemCount == 0) { // 모든 사람이 다 풀면 끝
                checkAndReduceScore(team_code, Boolean.FALSE);
                break;
            }
            if (LocalDateTime.now().isAfter(getGlobalBombLimitTime())){ // 시간 초과하면 점수 깎이고 끝
                checkAndReduceScore(team_code, Boolean.TRUE);
                break;
            }
            if (check(team_code, localDateTime)) { // 만약 풀었으면 (true 반환되었으면) tPC 1 감소시키고, 폭탄 재배정
                totalProblemCount -= 1;
                generateRandomBombPos(team_code, LocalDateTime.now(), globalBombLimitTime);
            }
            problemSolvercrawling.catch_solver(problems, members, team_code, level); // 푼 문제는 db 저장
        }

    }
}