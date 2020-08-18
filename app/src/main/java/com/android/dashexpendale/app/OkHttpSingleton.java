package com.android.dashexpendale.app;

import android.util.Base64;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpSingleton {
    private static final String AUTH = "Basic " + Base64.encodeToString(("user:123456").getBytes(), Base64.NO_WRAP);

    private static OkHttpSingleton singletonInstance;
    private OkHttpClient client;
    private OkHttpSingleton() {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)

                .build();
    }
    public static OkHttpSingleton getInstance(){

        if(singletonInstance==null){
            singletonInstance = new OkHttpSingleton();
        }
        return singletonInstance;
    }

    public OkHttpClient getClient() {
        return client;
    }


    public void closeConnections() {
        client.dispatcher().cancelAll();
    }


    public Request newCall(Request request) {
        return request.newBuilder().header("Authorization", AUTH).build();
    }
}
