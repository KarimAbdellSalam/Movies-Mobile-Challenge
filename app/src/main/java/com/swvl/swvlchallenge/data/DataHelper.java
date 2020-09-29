package com.swvl.swvlchallenge.data;

import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public interface DataHelper extends ApiHelper {
    public Observable<List<Movie>> loadAllMovies();
}
