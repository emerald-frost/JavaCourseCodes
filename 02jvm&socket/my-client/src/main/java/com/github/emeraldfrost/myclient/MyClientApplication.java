package com.github.emeraldfrost.myclient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;

public class MyClientApplication {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(1L))
                .readTimeout(Duration.ofSeconds(2L))
                .build();

        String url = "http://localhost:8801";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                final String respBody = response.body().string();
                System.out.println("ok, respBody: " + respBody);
            } else {
                System.out.println("failed, code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
