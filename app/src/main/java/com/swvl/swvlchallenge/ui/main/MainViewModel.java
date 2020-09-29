package com.swvl.swvlchallenge.ui.main;

import android.os.Handler;

import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;
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

    private  IMainInteractor interactor;
    ObservableField<List<Movie>> moviesData = new ObservableField<>(new ArrayList<>());

    public MovieListAdapter adapter = new MovieListAdapter(new ArrayList<>());

    @Inject
    public MainViewModel() {
        ApplicationComponent appComponent = getAppComponent();
        if (appComponent != null)
            interactor = appComponent.getMainInteractor();
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
        showLoading();
        interactor.loadAllMovies(interactorCallback);
        adapter.setOnInteractionListener(this);
    }
    InteractorCallback<List<Movie>> interactorCallback = new InteractorCallback<List<Movie>>() {
        @Override
        public void onSuccess(List<Movie> movies) {
            hideLoading();
            moviesData.set(movies);
            moviesData.notifyChange();
        }

        @Override
        public void onFailed(Throwable errors) {
            hideLoading();
        }
    };
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

    public void setInteractor(MainInteractor interactor) {
        this.interactor = interactor;
    }
}
