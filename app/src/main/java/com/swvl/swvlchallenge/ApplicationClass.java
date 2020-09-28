package com.swvl.swvlchallenge;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.swvl.swvlchallenge.dagger.AppModule;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;
import com.swvl.swvlchallenge.dagger.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class ApplicationClass extends Application implements HasActivityInjector {

    private static ApplicationComponent appComponent;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    public static ApplicationComponent getAppComponent() {
        return appComponent;
    }

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


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
