package com.example.baekboom.backend.service;


import com.example.baekboom.backend.entity.TeamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dao.teamDao;
import com.example.baekboom.backend.Constant.Tier;
import com.example.baekboom.backend.dto.teamDto;

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
        memberDao.saveMember(team_code,ID);
    }

    // 처음에 팀을 만들 때 사용되는 Service 구문
    public void teamJoin(String team_code, String team_leader, String level){
        Tier tier = Tier.valueOf(level);
        Long ordinal = Long.valueOf(tier.ordinal());
        teamDao.saveTeam(team_code, ordinal, team_leader);
        memberDao.saveMember(team_code, team_leader);
    }

    public void changed_level(String team_code, String level){
        Tier tier = Tier.valueOf(level);
        Long ordinal = Long.valueOf(tier.ordinal());
        teamDto team = teamDao.getTeam(team_code);

        teamDao.saveTeam(team_code, ordinal, team.getTeam_leader());
    }

    public void member_delete(String id){
        memberDao.member_delete(id);
    }

}
