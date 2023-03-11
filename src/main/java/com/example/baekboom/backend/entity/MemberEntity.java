package com.example.baekboom.backend.entity;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@SuperBuilder
@NoArgsConstructor
@DynamicInsert
@Setter
@Getter
@Entity
@Table(name="member")
public class MemberEntity implements UserDetails {
    @Id
    @Column(name = "userid") // 개인 고유 아이디
    private String userid;

    @Column(name = "score", columnDefinition = "INT NOT NULL default 0")
    private Long score;

    @Column(name = "bomb_yn", columnDefinition = "BOOLEAN NOT NULL default 0")
    private Boolean bombyn;

    @ManyToOne
    @JoinColumn(name = "team_code")
    private TeamEntity team;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ProblemEntity> problems;

    public void setTeam(TeamEntity team){
        this.team = team;
        if (!team.getMembers().contains(this)){
            team.getMembers().add(this);
        }
    }

//    public void setBombPos(String bomb_pos) {
//        this.bomb_pos = bomb_pos;
//    }

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }




}
