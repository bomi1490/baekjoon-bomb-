package com.example.baekboom.backend.service;


import com.example.baekboom.backend.crawling.ProblemSolvercrawling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.baekboom.backend.dao.memberDao;
import com.example.baekboom.backend.dto.*;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class LoginService {
    private memberDao memberDao;

    @Autowired
    public LoginService(memberDao memberDao){
        this.memberDao =memberDao;
    }


}
