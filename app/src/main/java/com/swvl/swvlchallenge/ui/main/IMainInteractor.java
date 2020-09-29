package com.swvl.swvlchallenge.ui.main;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public interface IMainInteractor {
    void loadAllMovies(InteractorCallback<List<Movie>> callback);

    void searchMovies(List<Movie> movies, String query, InteractorCallback<List<DataItem>> callback);
}
