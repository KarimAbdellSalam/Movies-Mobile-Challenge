package com.swvl.swvlchallenge.ui.movie;

import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class MovieViewModel extends BaseViewModel {

    ObservableField<Movie> movieObservable = new ObservableField<>();
    ObservableField<List<FlickrResponse.Photo>> flickrPhotos = new ObservableField<>(new ArrayList<>());
    ImagesListAdapter adapter = new ImagesListAdapter(new ArrayList<>());

    @Inject
    public MovieViewModel() {
    }

    public void setMovie(Movie movie) {
        movieObservable.set(movie);
        movie.notifyChange();
    }

    public void loadFlickrImages() {
        //todo lead flickr data
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

    public ObservableField<Movie> getMovie() {
        return movieObservable;
    }

    public ObservableField<List<FlickrResponse.Photo>> getPhotos() {
        return flickrPhotos;
    }

    public ImagesListAdapter getAdapter() {
        return adapter;
    }
}
