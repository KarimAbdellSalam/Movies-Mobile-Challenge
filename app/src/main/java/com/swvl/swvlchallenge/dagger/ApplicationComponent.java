package com.swvl.swvlchallenge.dagger;

import android.content.Context;

import com.swvl.swvlchallenge.ApplicationClass;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class,})
public interface ApplicationComponent {
    Context context();
    void inject(ApplicationClass applicationClass);
}
