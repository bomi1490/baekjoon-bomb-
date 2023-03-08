package com.example.baekboom.backend.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class teamDto {

    private String team_code;
    private Long level;
    private String bomb_pos;
    private String team_leader;

    public teamDto(String team_code, Long level, String bomb_pos, String team_leader){
        this.team_code = team_code;
        this.level = level;
        this.bomb_pos = bomb_pos;
        this.team_leader = team_leader;
    }

}
