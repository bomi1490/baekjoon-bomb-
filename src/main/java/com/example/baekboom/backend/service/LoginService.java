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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.baekboom.backend.repository.memberRepository;

import java.util.ArrayList;
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
    public LoginService(memberDao memberDao, AuthenticationManager authenticationManager){
        this.memberDao =memberDao;
        this.authenticationManager = authenticationManager;
    }

    public String login_teamName(String ID){
        String team_name = memberDao.getMember(ID).getTeam_code();
        return team_name;
    }
    // ?????? ??????


    public Tuple<memberDto, List<memberRankDto>> login(String ID){
        Tuple<memberDto, List<memberRankDto>> login_info = new Tuple<>();
        List<memberRankDto> memberRankDtos = new ArrayList<>();

        // ?????? ????????????
        memberDto memberDto = memberDao.getMember(ID);
        Map<String, Long> Team_members = memberDao.getTeamMember(memberDto.getTeam_code());
        List<String> KeySet = new ArrayList<>(Team_members.keySet());
        KeySet.sort((o1, o2) -> Team_members.get(o2).compareTo(Team_members.get(o1)));

        // ????????? ????????? ??????
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
        // 1. Login ?????? ?????????, ???????????? ???????????? Authentication ?????? ??????
        // ?????? authentication ??? ?????? ????????? ???????????? authenticated ?????? false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user_id, team_code);
        System.out.println("authenticationToken :::" + authenticationToken);
        // 2. ?????? ?????? (????????? ???????????? ??????)??? ??????????????? ??????
        // authenticate ???????????? ????????? ??? CustomUserDetailsService ?????? ?????? loadUserByUsername ???????????? ??????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        System.out.println("authentication :::" + authentication);
        // 3. ?????? ????????? ???????????? JWT ?????? ??????
        TokenInfo tokenInfo = JwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }


}
