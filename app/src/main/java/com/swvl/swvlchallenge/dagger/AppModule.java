package com.swvl.swvlchallenge.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swvl.swvlchallenge.ApplicationClass;
import com.swvl.swvlchallenge.BuildConfig;
import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.Repository;
import com.swvl.swvlchallenge.utils.Utils;
import com.swvl.swvlchallenge.utils.rx.AppSchedulerProvider;
import com.swvl.swvlchallenge.utils.rx.SchedulerProvider;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */

@Module
public class AppModule {
    private final
    ApplicationClass application;

    public AppModule(ApplicationClass application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    public DataHelper provideNetworkCalls(Repository repository) {
        return repository;
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() { // TODO add custom interceptor class if u need to add header and some Data
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .addInterceptor(logging)
                .connectTimeout(Utils.Const.HTTP.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Utils.Const.HTTP.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Utils.Const.HTTP.READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitBuilder(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BuildConfig.FLICKR_BASE_URL) //todo ADD base url later
                .build(); // Base URL to create instance
    }
    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

}
