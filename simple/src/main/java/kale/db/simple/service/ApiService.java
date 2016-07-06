package kale.db.simple.service;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Request;

/**
 * @author Kale
 * @date 2016/7/5
 */

public class ApiService {

    private okhttp3.OkHttpClient client;

    public ApiService() {
        client = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public <T> Call doGet(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }
}
