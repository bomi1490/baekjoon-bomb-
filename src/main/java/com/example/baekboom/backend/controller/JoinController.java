package com.example.baekboom.backend.controller;


import com.example.baekboom.backend.service.JoinService;
import com.example.baekboom.backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class JoinController {

    private JoinService joinService;
    private LoginService loginService;

    @Autowired
    public JoinController(JoinService joinService, LoginService loginService){
        this.joinService = joinService;
        this.loginService = loginService;
    }

    // 대문
    @GetMapping("/")
    public String index(){

        return "";
    }



    @PostMapping("/team_building")
    public void team_make(@RequestBody String inputCode, @RequestBody String inputId){
        // 팀 조인
        joinService.teamJoin(inputCode, inputId, "1");
    }


    @PostMapping("/member")
    public void member_make(@RequestBody String inputCode, @RequestBody String inputId){
        // 멤버 넣기
        joinService.memberJoin(inputCode, inputId);

    }


    @PostMapping("/level")
    public void change_level(@RequestBody String inputCode, @RequestBody String level){
        joinService.changed_level(inputCode, level);
    }

    @DeleteMapping("/member")
    public void delete_member(@RequestBody String inputId){
        joinService.member_delete(inputId);
    }




}

