package com.example.baekboom.backend.controller;


import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.dto.memberRankDto;
import com.example.baekboom.backend.service.LoginService;
import com.example.baekboom.backend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LoginController {

    private final TokenService tokenService;
    private final LoginService loginService;

    @Autowired
    public LoginController(TokenService tokenService, LoginService loginService){
        this.tokenService = tokenService;
        this.loginService = loginService;
    }

    @GetMapping("id_login")
    public String login_page(){
        return "로그인하는 페이지";
    }

    @PostMapping("/id_login")
    public String register(Model model, @RequestBody String token, @RequestParam String Id){
        tokenService.register(Id, token);
        LoginService.Tuple<memberDto, List<memberRankDto>> tuple = loginService.login(Id);
        // member에 대한 DTO가 전송 : id, 점수, 폭탄 유무, 팀 코드 등
        model.addAttribute("member", tuple.getMemberDto());
        // rank에 대한 DTO들이 전송 : id, 점수, 등수가 담겨있는 Dto의 List가 전송된다.
        model.addAttribute("rank", tuple.getLst_rank());
        String url = String.format("redirect:/personal/%s/%s", tuple.getMemberDto().getTeam_code(), tuple.getMemberDto().getUser_id());
        return url;
    }
}
