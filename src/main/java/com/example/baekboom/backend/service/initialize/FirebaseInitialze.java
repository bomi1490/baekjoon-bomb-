package com.example.baekboom.backend.service.initialize;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitialze {
    // 처음에 토큰을 만들고 저장하는 곳이 필요함
    @PostConstruct
    private void getAccessToken() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("resource/firebase/beakbombalram-firebase-adminsdk-xnwj6-6965f813ae.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}
