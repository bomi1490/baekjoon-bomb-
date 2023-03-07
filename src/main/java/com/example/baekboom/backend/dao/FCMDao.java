package com.example.baekboom.backend.dao;


import com.example.baekboom.backend.repository.tokenRepository;
import com.example.baekboom.backend.entity.TokenEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FCMDao {

    private final tokenRepository tokenRepository;
    @Autowired
    public FCMDao (tokenRepository tokenRepository){
        this.tokenRepository = tokenRepository;
    }


    // 토큰을 저장하는 메서드
    public void saveToken(String id, String team_code, String fcmtoken){
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setFcmtoken(fcmtoken);
        tokenEntity.setUser(id);
        tokenEntity.setTeam(team_code);
        tokenRepository.save(tokenEntity);
        
    }

    public void deleteToken(String id){
        tokenRepository.deleteById(id);
    }

    
    // 저장한 토큰들을 team 이름으로 repository에서 가져오고 List로 가져옴
    public List<String> getfcmToken(String team){
        List<String> tokens = new ArrayList<>();
        List<TokenEntity> tokenEntities = tokenRepository.findAllByTeam(team);
        tokenEntities.forEach(item -> tokens.add(item.getFcmtoken()));
        return tokens;
    }
}
