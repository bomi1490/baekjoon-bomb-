package com.example.baekboom.backend.controller;


import com.example.baekboom.backend.service.FirebaseService;
import com.example.baekboom.backend.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SSEController {

    private final TokenService tokenService;

    public SSEController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody String token, @RequestParam String Id){
        tokenService.register(Id, token);
        return ResponseEntity.ok().build();
    }
}
