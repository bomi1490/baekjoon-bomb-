package com.example.baekboom.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dao.teamDao;
import com.example.baekboom.backend.Constant.Tier;

@Service
public class JoinService {
    private memberDao memberDao;
    private teamDao teamDao;


    @Autowired
    public JoinService(memberDao memberDao, teamDao teamDao){
        this.memberDao = memberDao;
        this.teamDao = teamDao;
    }

    // 회원 가입할 때 사용되는 Service 구문
    public void memberJoin(String team_code, String ID){
        if (memberDao.getTeamMember(team_code).isEmpty()){ // 만약 팀원이 없으면 팀장으로 설정하려고 함
            // pass
        } else {
            memberDao.saveMember(team_code,ID);
        }
    }

    // 처음에 팀을 만들 때 사용되는 Service 구문
    public void teamJoin(String team_code, String team_leader, String level){
        Tier tier = Tier.valueOf(level);
        Long ordinal = Long.valueOf(tier.ordinal());
        teamDao.saveTeam(team_code, ordinal, team_leader);
        memberDao.saveMember(team_code, team_leader);
    }

}
