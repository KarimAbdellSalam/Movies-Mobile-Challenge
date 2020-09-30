package com.swvl.swvlchallenge.ui.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swvl.swvlchallenge.utils.Utils;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Karim Abdell Salam on 30,September,2020
 */
public class FactoryForTest {
    public static class Network {
        public static Retrofit buildRetrofit(MockWebServer mockWebServer) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(Utils.Const.HTTP.TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Utils.Const.HTTP.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Utils.Const.HTTP.READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder().setLenient().create();
            return new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(mockWebServer.url("/"))
                    .build();
        }
    }

    public static class Converters {
        public static Gson provideGson() {
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
    }
}
