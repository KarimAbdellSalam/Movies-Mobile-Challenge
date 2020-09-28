package com.swvl.swvlchallenge;

import android.app.Application;

import com.swvl.swvlchallenge.dagger.AppModule;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;
import com.swvl.swvlchallenge.dagger.DaggerApplicationComponent;

import dagger.android.AndroidInjection;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class ApplicationClass extends Application {

    private static ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initBuild();
    }


    private void initBuild() {
        appComponent = DaggerApplicationComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
