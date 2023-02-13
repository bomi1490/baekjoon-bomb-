package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@SuperBuilder
@NoArgsConstructor
@DynamicInsert
@Setter
@Getter
@Entity
@Table(name="member")
public class MemberEntity {
    @Id
    @Column(name = "user_id") // 개인 고유 아이디
    private String user_id;

    @NotNull
    @Column(name = "id") // 개인 닉네임
    private String name;

    @Column(name = "score", columnDefinition = "INT NOT NULL default 0")
    private Long score;

    @Column(name = "bomb_yn", columnDefinition = "BOOLEAN NOT NULL default 0")
    private Boolean bomb_yn;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProblemEntity> problems = new ArrayList<>();


    public void setTeam(TeamEntity team){
        this.team = team;
        if (!team.getMembers().contains(this)){
            team.getMembers().add(this);
        }
    }


}
