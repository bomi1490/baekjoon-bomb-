package com.example.baekboom.backend.dao;

import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.repository.memberRepository;
import com.example.baekboom.backend.repository.teamRepository;

import javax.transaction.Transactional;

@Component
public class memberDao {
    private final memberRepository memberrepository;
    private final teamRepository teamRepository;

    @Autowired
    public memberDao(memberRepository memberrepository, teamRepository teamRepository){
        this.memberrepository = memberrepository;
        this.teamRepository =teamRepository;
    }

    // 회원 가입
    public void saveMember(memberDto member, String team_code){
        MemberEntity memberEntity = new MemberEntity();
        TeamEntity team = teamRepository.getReferenceById(team_code);
        memberEntity.setUser_id(memberEntity.getUser_id());
        memberEntity.setName(memberEntity.getName());
        memberEntity.setScore(0L);
        memberEntity.setBomb_yn(false);
        memberEntity.setTeam(team);
        memberrepository.save(memberEntity);
    }

    public memberDto getMember(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberDto member = new memberDto(memberEntity.getUser_id(),memberEntity.getName(),
                memberEntity.getScore(), memberEntity.getBomb_yn(), memberEntity.getTeam().getTeam_code());

        return member;
    }

    public void set_Bomb_True(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberEntity.setBomb_yn(true);
        memberrepository.save(memberEntity);
    }
    public void set_Bomb_False(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberEntity.setBomb_yn(false);
        memberrepository.save(memberEntity);
    }

    public void updateScore(String user_id, Long plusScore){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        Long score = memberEntity.getScore() + plusScore;
        memberEntity.setScore(score);
        memberrepository.save(memberEntity);
    }

    @Transactional
    public void delete(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberrepository.delete(memberEntity);
    }
}
