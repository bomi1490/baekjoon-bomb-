package com.example.baekboom.backend.dao;

import com.example.baekboom.backend.entity.MemberEntity;
import org.springframework.stereotype.Component;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.repository.memberRepository;

@Component
public class memberDao {
    private final memberRepository memberrepository;

    public memberDao(memberRepository memberrepository){
        this.memberrepository = memberrepository;
    }

    // 회원 가입
    public void saveMember(memberDto member){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUser_id(memberEntity.getUser_id());
        memberEntity.setName(memberEntity.getName());
        memberEntity.setScore(0L);
        memberEntity.setBomb_yn(false);
        memberrepository.save(memberEntity);
    }

    public memberDto getMember(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberDto member = new memberDto(memberEntity.getUser_id(),memberEntity.getName(),
                memberEntity.getScore(), memberEntity.getBomb_yn(), memberEntity.getTeam().getTeam_name());

        return member;
    }

    public void updateScore(String user_id, Long plusScore){

    }


    public void delete(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberrepository.delete(memberEntity);
    }
}
