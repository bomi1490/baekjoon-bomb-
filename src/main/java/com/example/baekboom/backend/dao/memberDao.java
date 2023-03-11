package com.example.baekboom.backend.dao;

import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.entity.MemberEntity;
import com.example.baekboom.backend.entity.TeamEntity;
import com.example.baekboom.backend.repository.memberRepository;
import com.example.baekboom.backend.repository.teamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class memberDao {
    private final memberRepository memberrepository;
    private final teamRepository teamRepository;

    // memberRepositoryImpl 추가로 인한 @Qualifier 추가
    @Autowired
    public memberDao(@Qualifier("memberRepository") memberRepository memberrepository, teamRepository teamRepository){
        this.memberrepository = memberrepository;
        this.teamRepository =teamRepository;
    }

    // 회원 가입할 때 사용되는 메소드
    public void saveMember(String team_code, String ID){
        MemberEntity memberEntity = new MemberEntity();
        TeamEntity team = teamRepository.getReferenceById(team_code);
        memberEntity.setUserid(ID);
        //memberEntity.setName(member.getName());
        memberEntity.setScore(0L);
        memberEntity.setBombyn(false);
        memberEntity.setTeam(team);
        memberrepository.save(memberEntity);
    }

    // 회원 가입 할때 필요
    public Map<String, Long> getTeamMember(String team_code){
        Map<String, Long> map = new HashMap<>();

        List<MemberEntity> memberEntities = memberrepository.findAllByTeam_Teamcode(team_code);

        memberEntities.forEach(item -> map.put(item.getUserid(), item.getScore()));

        return map;
    }

    // entity로 수정
    public memberDto getMember(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberDto member = new memberDto(memberEntity.getUserid(),
                memberEntity.getScore(), memberEntity.getBombyn(), memberEntity.getTeam().getTeamcode());

        return member;
    }

    public Boolean get_Bomb(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        return memberEntity.getBombyn();
    }

    public void set_Bomb_True(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberEntity.setBombyn(true);
        memberrepository.save(memberEntity);
    }
    public void set_Bomb_False(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberEntity.setBombyn(false);
        memberrepository.save(memberEntity);
    }

    public void updateScore(String user_id, Long plusScore){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        Long score = memberEntity.getScore() + plusScore;
        memberEntity.setScore(score);
        memberrepository.save(memberEntity);
    }

    @Transactional
    public void member_delete(String user_id){
        MemberEntity memberEntity = memberrepository.getReferenceById(user_id);
        memberrepository.delete(memberEntity);
    }
}
