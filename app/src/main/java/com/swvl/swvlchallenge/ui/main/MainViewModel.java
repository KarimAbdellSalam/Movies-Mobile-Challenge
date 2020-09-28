package com.swvl.swvlchallenge.ui.main;

import android.os.Handler;

import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.BaseViewHolder;
import com.swvl.swvlchallenge.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class MainViewModel extends BaseViewModel implements IMainViewModel, OnInteractionListener {

    ObservableField<List<Movie>> moviesData = new ObservableField<>(new ArrayList<>());
    public MovieListAdapter adapter = new MovieListAdapter(new ArrayList<>());

    @Inject
    public MainViewModel() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void loadData() {
        //todo load all movies
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setTitle("Test");
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        moviesData.set(movies);

    }

    @Override
    public void onItemClicked(Movie item) {
        //todo go to Movie screen
    }

    public ObservableField<List<Movie>> getMoviesData() {
        return moviesData;
    }

    public void setMoviesData(ObservableField<List<Movie>> moviesData) {
        this.moviesData = moviesData;
    }

    public MovieListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MovieListAdapter adapter) {
        this.adapter = adapter;
    }
}
