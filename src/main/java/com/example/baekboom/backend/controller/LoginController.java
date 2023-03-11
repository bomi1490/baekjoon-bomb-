package com.example.baekboom.backend.controller;


import com.example.baekboom.backend.dto.memberDto;
import com.example.baekboom.backend.service.LoginService;
import com.example.baekboom.backend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.baekboom.backend.dto.TokenInfo;

@Slf4j
@RestController
@RequestMapping("/members")
public class LoginController {

    private final TokenService tokenService;
    private final LoginService loginService;

    @Autowired
    public LoginController(TokenService tokenService, LoginService loginService){
        this.tokenService = tokenService;
        this.loginService = loginService;
    }



    // 만약 안되면 map이 아니라 String으로 바꿔보기
  /*  @GetMapping("/member")
    public Map<String, String> register(@RequestBody String token, @RequestParam String inputId){
        Map<String, String> map = new HashMap<>();
        String team_code = loginService.login_teamName(inputId);
        map.put("inputCode", team_code);

        return map;
    }*/

    @PostMapping("/login")
    public TokenInfo login(@RequestBody memberDto memberDto) {
        String user_id = memberDto.getUser_id();
        String team_code = memberDto.getTeam_code();
        TokenInfo tokenInfo = LoginService.login(user_id, team_code);
        return tokenInfo;
    }
}
