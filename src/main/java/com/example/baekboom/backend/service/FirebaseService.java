package com.example.baekboom.backend.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseService {

    private String firebaseConfigPath = "firebase/beakbombalram-firebase-adminsdk-xnwj6-6965f813ae.json";

    // FirebaseMessaging.getInstance().subscribeToTopic("news"); 그룹화 하는 방법
    public void sendMessage(String token, String id, String date) throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);

        String m = id +"님께서" +date +"에 문제를 풀었습니다!";
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        Message fcmMessage = Message.builder()
                .putData("title", "백준 폭탄 프로그램")
                .putData("body", m)
                .setToken(token)
                .build();

        FirebaseMessaging.getInstance().sendAsync(fcmMessage);
    }


}
