package com.example.pccontrol.Core;

import com.example.pccontrol.ConsoleFragment;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpConnect {


    private static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private ConsoleFragment consoleFragment;


    public static void get(String url, HttpCallback callBack) {
        new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    String body = response.body().string();
                    callBack.onSuccess(response.code(), body);
                }


            } catch (IOException ex) {
                callBack.onError(ex.getMessage());
            }
        }).start();
    }

    public static void post(String url, String bodyText, HttpCallback callback) {
        new Thread(() -> {
            try {
                RequestBody body = RequestBody.create(bodyText, MediaType.parse("text/plain"));
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    callback.onSuccess(response.code(), responseBody);
                }
            } catch (IOException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    // POST запрос (JSON)
    public static void postJson(String url, String json, HttpCallback callback) {
        new Thread(() -> {
            try {
                RequestBody body = RequestBody.create(json, JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBody = response.body().string();
                    callback.onSuccess(response.code(), responseBody);
                }
            } catch (IOException e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }

    public void setConsoleFragment(ConsoleFragment consoleFragment) {
        this.consoleFragment = consoleFragment;
    }


    public interface HttpCallback {
        void onSuccess(int code, String repsonse);

        void onError(String error);

    }


}
