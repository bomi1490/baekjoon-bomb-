package com.example.baekboom.backend.service.initialize;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseInitialze {
    // 처음에 토큰을 만들고 저장하는 곳이 필요함

    private static final String FIREBASE_CONFIG_PATH = "firebase/beakbombalram-firebase-adminsdk-xnwj6-6965f813ae.json";
    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);

            }
        } catch (IOException e) {

        }
//    private void getAccessToken() throws IOException {
//
//        FileInputStream serviceAccount =
//                new FileInputStream("resource/firebase/beakbombalram-firebase-adminsdk-xnwj6-6965f813ae.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        FirebaseApp.initializeApp(options);
//    }
    }
}
