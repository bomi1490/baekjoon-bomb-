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


    public void saveTeam(teamDto team){
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeam_code(team.getTeam_code());
        teamEntity.setLevel(team.getLevel());
        teamEntity.setBomb_pos(null);
        teamEntity.setTeam_leader(team.getTeam_leader());
        teamRepository.save(teamEntity);
    }

}
