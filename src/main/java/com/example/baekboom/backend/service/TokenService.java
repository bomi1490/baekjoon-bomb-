package com.example.baekboom.backend.service;

import com.google.firebase.auth.internal.DownloadAccountResponse;
import com.google.firebase.auth.internal.GetAccountInfoResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private final Map<String, String> tokenMap = new HashMap<>();

    public void register(String user_id, String token){
        tokenMap.put(user_id, token);
    }

    private void createReceiveNotification(DownloadAccountResponse.User receiver){
        if (receiver.is)
    }
}
