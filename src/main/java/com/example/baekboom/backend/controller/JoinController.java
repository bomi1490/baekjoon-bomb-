package com.example.baekboom.backend.controller;


import com.example.baekboom.backend.service.JoinService;
import com.example.baekboom.backend.service.LoginService;
import com.example.baekboom.backend.service.LoginService.Tuple;
import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.dto.memberRankDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class JoinController {

    private JoinService joinService;
    private LoginService loginService;

    @Autowired
    public JoinController(JoinService joinService, LoginService loginService){
        this.joinService = joinService;
        this.loginService = loginService;
    }


    @GetMapping("/")
    public String index(){

        return "index";
    }

    @GetMapping("/team_building")
    public String team_make_page(){
        return "팀장 구현하는 화면";
    }


    @PostMapping("/team_building")
    public String team_make(Model model, @RequestParam String team_code, @RequestParam String team_leader){
        // 팀 조인
        joinService.teamJoin(team_code, team_leader, "1");

        return "redirect:/";
    }

    @GetMapping("/join")
    public String member_make_page(){

        return "멤버 생성하는 화면";
    }

    @PostMapping("join")
    public String member_make(Model model, @RequestParam String team_code, @RequestParam String member_id){
        // 멤버 넣기
        joinService.memberJoin(team_code, member_id);

        return "redirect:/";
    }





}
