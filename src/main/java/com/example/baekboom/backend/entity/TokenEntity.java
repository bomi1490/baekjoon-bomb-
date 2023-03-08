package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuperBuilder
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name="token")
public class TokenEntity {
    @Id
    @Column(name = "user_id") // 개인 고유 아이디
    private String user;

    @NotNull
    @Column(name = "FCMtoken") // 개인 토큰
    private String fcmtoken;

    @NotNull
    @Column(name = "team") // 팀 이름 -> 서칭의 용이성을 위함
    private String team;
}
