package com.swvl.swvlchallenge.ui.main;

import android.util.Log;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class MainInteractor implements IMainInteractor {
    private  DataHelper dataHelper;
    private  SchedulerProvider schedulerProvider;

    @Inject
    public MainInteractor(DataHelper dataHelper, SchedulerProvider schedulerProvider) {
        this.dataHelper = dataHelper;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void loadAllMovies(InteractorCallback<List<Movie>> callback) {
        dataHelper.loadAllMovies()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Movie> movies) {
                        callback.onSuccess(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("loading error", e.getMessage());
                        callback.onFailed(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
