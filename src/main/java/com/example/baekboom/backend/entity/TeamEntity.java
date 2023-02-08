package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Team")
public class TeamEntity {
    //level int, bomb, team_code, 팀 코드, 구성원
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long team_id;

    @NotNull
    @Column(name = "level")
    private Long level;

    @Column(name = "bomb")
    private String bomb_pos;

    @NotNull
    @Column(name = "team_code")
    private String team_code;


}
