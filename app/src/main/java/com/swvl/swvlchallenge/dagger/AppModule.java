package com.swvl.swvlchallenge.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.swvl.swvlchallenge.ApplicationClass;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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

}
