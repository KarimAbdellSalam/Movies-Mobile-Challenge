package com.swvl.swvlchallenge.manager;

import android.app.Activity;
import android.content.Intent;

import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.main.MainActivity;
import com.swvl.swvlchallenge.utils.Utils;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class ScreenControl {
    public static void openMainActivity(Activity activity) {
        activity.startActivity(MainActivity.newIntent(activity));
    }

    public static void openMovieActivity(Activity activity, Movie movie) {
        //todo
//        Intent intent = MovieActivity.newIntent(activity);
//        intent.putExtra(Utils.Const.Ref.MOVIE, movie);
//        activity.startActivity(intent);
    }
}
