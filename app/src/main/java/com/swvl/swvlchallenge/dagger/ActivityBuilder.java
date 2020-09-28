package com.swvl.swvlchallenge.dagger;

import com.swvl.swvlchallenge.ui.main.MainActivity;
import com.swvl.swvlchallenge.ui.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class
    })
    abstract MainActivity bindMainActivity();
}
