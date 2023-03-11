package com.example.baekboom.backend.service;


import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.dto.memberRankDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    private memberDao memberDao;

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
    public LoginService(memberDao memberDao){
        this.memberDao =memberDao;
    }

    public String login_teamName(String ID){
        String team_name = memberDao.getMember(ID).getTeam_code();
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


}
