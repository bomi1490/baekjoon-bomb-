package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="team")
public class TeamEntity {
    //level int, bomb, team_code, 팀 코드, 구성원
    @Id
    @Column(name = "team_code")
    private String teamcode;

    @NotNull
    @Column(name = "level")
    private Long level;

    @Column(name = "bomb_pos")
    private String bomb_pos;

    @Column(name = "team_leader")
    private String team_leader;

    @OneToMany(mappedBy = "team", fetch =FetchType.LAZY)
    private List<MemberEntity> members;



}
