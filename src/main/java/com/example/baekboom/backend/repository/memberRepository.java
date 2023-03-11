package com.example.baekboom.backend.repository;

import com.example.baekboom.backend.entity.MemberEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
public interface memberRepository extends JpaRepository<MemberEntity,String> {
    @Override
    MemberEntity getReferenceById(String user_id); // 원하는 Member를 id로 찾아오는 기능

    List<MemberEntity> findAllByTeam_Teamcode(String team_code);


    MemberEntity findByBombyn(Boolean bomb_yn);

    Optional<Member> findByMemberId(String username);


}
