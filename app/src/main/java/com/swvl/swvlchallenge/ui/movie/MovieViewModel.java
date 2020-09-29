package com.swvl.swvlchallenge.ui.movie;

import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.BaseViewModel;
import com.swvl.swvlchallenge.utils.Utils;

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
    private IMovieInteractor interactor;

    @Inject
    public MovieViewModel() {
        ApplicationComponent appComponent = getAppComponent();
        if (appComponent != null)
            interactor = appComponent.getMovieInteractor();
    }

    public void setMovie(Movie movie) {
        movieObservable.set(movie);
        movie.notifyChange();
    }

    //-----
    private int currentPage = 1;
    private int MAX_PAGES = 4;
    private int MIN_IMAGES = 4;
    private int PER_PAGE = 300;

    public void loadFlickrImages() {
        setIsLoading(true);
        final Movie movie = movieObservable.get();
        if (movie == null)
            return;
        interactor.searchPhotos(currentPage, PER_PAGE, movie.getTitle(), new InteractorCallback<FlickrResponse>() {
            @Override
            public void onSuccess(FlickrResponse flickrResponse) {
                //filter flickr list, it may reduce the number of fetched image
                interactor.filterImages(flickrResponse, movie.getTitle(), new InteractorCallback<List<FlickrResponse.Photo>>() {
                    @Override
                    public void onSuccess(List<FlickrResponse.Photo> photos) {
                        //todo check the list size to get at least ${MIN_IMAGES} images of exact movie
                        //todo if there are no enough photos fetch another page (at most ${MAX_PAGES})
                        displayImages(photos);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        showToast(throwable.getMessage());
                    }
                });
            }

            @Override
            public void onFailed(Throwable errors) {
                setIsLoading(false);
            }
        });
    }

    private void displayImages(List<FlickrResponse.Photo> photos) {
        flickrPhotos.set(photos);
        flickrPhotos.notifyChange();
    }

    private void showToast(String message) {
        showToast(message);
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

    public void setInteractor(MovieInteractor interactor) {
        this.interactor = interactor;
    }
}
