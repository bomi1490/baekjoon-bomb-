package com.example.baekboom.backend.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    public void sendMessage(String Id, String date, String registrationToken) throws InterruptedException, ExecutionException {
        String m = Id +"님께서" +date +"에 문제를 풀었습니다!";
        Message message = Message.builder()
                .setToken(registrationToken)
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl","300")
                        .setNotification(new WebpushNotification("alram",m))
                        .build())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

    }
}
