package com.swvl.swvlchallenge.ui.movie;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.utils.AppLogger;
import com.swvl.swvlchallenge.utils.Utils;
import com.swvl.swvlchallenge.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class MovieInteractor implements IMovieInteractor{
    private final SchedulerProvider schedulerProvider;
    private DataHelper dataHelper;

    @Inject
    public MovieInteractor(DataHelper dataHelper, SchedulerProvider schedulerProvider) {
        this.dataHelper = dataHelper;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public void searchPhotos(int currentPage, int perPage, String query, InteractorCallback<FlickrResponse> callback) {
        dataHelper.searchImagesByMovieName(query, currentPage, perPage)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<FlickrResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(FlickrResponse response) {
                        if (!response.getStat().equalsIgnoreCase(Utils.Const.HTTP.OK)) {
                            return;
                        }
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // please handle error message here and remove callback
                        AppLogger.d(Utils.Const.HTTP.API_FAILED);
                        callback.onFailed(e);
                    }
                });

    }

    @Override
    public void filterImages(FlickrResponse flickrResponse, String title, InteractorCallback<List<FlickrResponse.Photo>> callback) {
        dataHelper.filterMovies(flickrResponse, title)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<List<FlickrResponse.Photo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(List<FlickrResponse.Photo> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }



}
