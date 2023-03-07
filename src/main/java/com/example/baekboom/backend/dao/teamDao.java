package com.example.baekboom.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.repository.teamRepository;
import com.example.baekboom.backend.dto.teamDto;

@Component
public class teamDao {

    private teamRepository teamRepository;

    @Autowired
    public teamDao(teamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    // 팀이 맨 처음 만들어질 때 사용되는 메소드
    public void saveTeam(String team_code, Long level, String team_leader){
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamcode(team_code);
        teamEntity.setLevel(level);
        teamEntity.setBomb_pos(null);
        teamEntity.setTeam_leader(team_leader);
        teamRepository.save(teamEntity);
    }

    public teamDto getTeam(String team_code){
        teamDto teamDto = new teamDto();

        TeamEntity team = teamRepository.getReferenceById(team_code);
        teamDto.setTeam_code(team.getTeamcode());
        teamDto.setLevel(team.getLevel());
        teamDto.setTeam_leader(team.getTeam_leader());
        teamDto.setBomb_pos(team.getBomb_pos());

        return teamDto;
    }

    public void changedTeamLeader(String teamLeader, String team_code){
        TeamEntity team = teamRepository.getReferenceById(team_code);
        team.setTeam_leader(teamLeader);
        teamRepository.save(team);
    }


}
