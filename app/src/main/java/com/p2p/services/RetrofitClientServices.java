package com.p2p.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.p2p.utils.PrefsUtils;
import com.p2p.utils.URLUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientServices {
    private static Retrofit retrofit;
    private static Retrofit retrofitForOtherSource;

    public static Retrofit getRetrofitInstanceForOtherSource(String baseUrl) {
        if (retrofitForOtherSource == null) {

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Cache-Control", "no-cache")
                            .addHeader("Cache-Control", "no-store");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofitForOtherSource = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitForOtherSource;

    }

    public static APIServices getAPIServicesForOtherSource(String urlBase) {
        return getRetrofitInstanceForOtherSource(urlBase).create(APIServices.class);
    }

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("Cache-Control", "no-cache")
                            .addHeader("Cache-Control", "no-store");

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(URLUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static APIServices getAPIServices() {
        return getRetrofitInstance().create(APIServices.class);
    }
}
