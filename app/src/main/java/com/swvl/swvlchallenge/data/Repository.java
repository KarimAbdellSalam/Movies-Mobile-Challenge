package com.swvl.swvlchallenge.data;

import android.content.Context;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class Repository implements DataHelper {
    Context context;

    @Inject
    public Repository(Retrofit retrofit, Context context) {
        this.context = context;
    }
}
