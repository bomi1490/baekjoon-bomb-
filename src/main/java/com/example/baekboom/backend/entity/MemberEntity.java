package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
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

//    @NotNull
//    @Column(name = "id") // 개인 닉네임
//    private String name;

    @Column(name = "score", columnDefinition = "INT NOT NULL default 0")
    private Long score;

    @Column(name = "bomb_yn", columnDefinition = "BOOLEAN NOT NULL default 0")
    private Boolean bomb_yn;

    @ManyToOne
    @JoinColumn(name = "team_code", referencedColumnName = "team_code")
    private TeamEntity team;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProblemEntity> problems;

    @Transient
    private String bomb_pos;

    public void setTeam(TeamEntity team){
        this.team = team;
        if (!team.getMembers().contains(this)){
            team.getMembers().add(this);
        }
    }

    public void setBombYn(Boolean bomb_yn) {
        this.bomb_yn = bomb_yn;
    }

    public void setBombPos(String bomb_pos) {
        this.bomb_pos = bomb_pos;
    }
}
