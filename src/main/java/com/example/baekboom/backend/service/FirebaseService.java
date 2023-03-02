package com.example.baekboom.backend.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

    private final String API_URL = "https://fcm.googleapis.com/v1/project/beakbombalram/messages:send";
    @Async
    public void sendMessage(String Id, String date, String registrationToken) {


        String m = Id +"님께서" +date +"에 문제를 풀었습니다!";

//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(m, MediaType.get("application/json; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; utf-8")
//                .build();
//
//        Response response = client.newCall(request).execute();

        Message message = Message.builder()
                .setToken(registrationToken)
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl","300")
                        .setNotification(new WebpushNotification("백준 폭탄 프로그램",m))
                        .build())
                .build();

        FirebaseMessaging.getInstance().sendAsync(message);

    }



    private String getAccessToken() throws IOException{
        String firebaseConfigPath = "firebase/beakbombalram-firebase-adminsdk-xnwj6-6965f813ae.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
