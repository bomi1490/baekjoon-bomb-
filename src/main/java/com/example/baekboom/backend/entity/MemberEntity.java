package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@SuperBuilder
@NoArgsConstructor
@DynamicInsert
@Setter
@Getter
@Entity
@Table(name="Member")
public class MemberEntity {
    @Id
    @Column(name = "user_id")
    private String user_id;

    @NotNull
    @Column(name = "id")
    private String name;

    @Column(name = "score", columnDefinition = "INT NOT NULL default 0")
    private Long score;

    @Column(name = "bomb_yn", columnDefinition = "BOOLEAN NOT NULL default 0")
    private Boolean bomb_yn;


    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;


}
