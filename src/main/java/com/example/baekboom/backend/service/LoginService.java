package com.example.baekboom.backend.service;


import com.example.baekboom.backend.config.JwtTokenProvider;
import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.dto.memberRankDto;
import com.example.baekboom.backend.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.baekboom.backend.repository.memberRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private memberDao memberDao;

    private memberRepository memberRepository;

    private static AuthenticationManagerBuilder authenticationManagerBuilder;
    private  JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;



    public class Tuple<memberDto, lst_rank>{


        private memberDto memberDto;
        private lst_rank lst_rank;

        public void put(memberDto memberDto, lst_rank lst_rank){
            this.memberDto = memberDto;
            this.lst_rank = lst_rank;
        }
        public memberDto getMemberDto(){
            return this.memberDto;
        }

        public lst_rank getLst_rank() {
            return this.lst_rank;
        }


    }

    @Autowired
    public LoginService(memberDao memberDao, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberDao =memberDao;
        this.authenticationManager = authenticationManager;
    }

    public String login_teamName(String ID){
        System.out.println("asdfasdf:::: "+ID);
        String team_name = memberDao.getMember(ID).getTeam_code();
        System.out.println(team_name);
        return team_name;
    }
    // 기성 코드


    public Tuple<memberDto, List<memberRankDto>> login(String ID){
        Tuple<memberDto, List<memberRankDto>> login_info = new Tuple<>();
        List<memberRankDto> memberRankDtos = new ArrayList<>();

        // 개인 가져오기
        memberDto memberDto = memberDao.getMember(ID);
        Map<String, Long> Team_members = memberDao.getTeamMember(memberDto.getTeam_code());
        List<String> KeySet = new ArrayList<>(Team_members.keySet());
        KeySet.sort((o1, o2) -> Team_members.get(o2).compareTo(Team_members.get(o1)));

        // 랭킹을 구하는 방법
        Long number = 10000L;
        int same_rank = 0;
        for (int i = 1; i < KeySet.size()+1; i++) {
            memberRankDto member = new memberRankDto();
            String id = KeySet.get(i-1);
            member.setUser_id(id);
            member.setScore(Team_members.get(KeySet.get(i-1)));
            member.setBombYn(memberDao.get_Bomb(id));
            if (number == Team_members.get(KeySet.get(i-1))){member.setRank(same_rank);}
            else {member.setRank(i);
                same_rank = i;}
            memberRankDtos.add(member);
        }
        login_info.put(memberDto, memberRankDtos);
        return login_info;
    }

    @Autowired
    public static TokenInfo login(String user_id, String team_code) {
        // 1. Login 유저 아이디, 팀코드를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 fail
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_id, team_code, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        authenticationToken.setDetails("1111");
        System.out.println("authenticationToken ::: "+ authenticationToken);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println(authentication);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = JwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }


}