package com.swvl.swvlchallenge.ui.main;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.BaseViewHolder;
import com.swvl.swvlchallenge.ui.base.BaseViewModel;
import com.swvl.swvlchallenge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class MainViewModel extends BaseViewModel<MainNavigator> implements IMainViewModel, OnInteractionListener {

    private IMainInteractor interactor;
    ObservableField<List<Movie>> moviesData = new ObservableField<>(new ArrayList<>());
    ObservableField<List<DataItem>> resultItems = new ObservableField<>(new ArrayList<>());

    public MovieListAdapter adapter = new MovieListAdapter(new ArrayList<>());
    public SearchResultAdapter searchResultAdapter = new SearchResultAdapter(new ArrayList<>());


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
        searchResultAdapter.setOnInteractionListener(this);
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
        getNavigator().openMovieActivity(item);
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            submit(Intent.ACTION_SEARCH, query);
            return false;
        }
    };

    SearchView.OnCloseListener onCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            submit(Intent.ACTION_SEARCH, "");
            return true;
        }
    };

    private Handler handler;
    private Runnable runnable;
    private static final long SUBMISSION_TIME = 200;

    //Auto query submission after SUBMISSION_TIME in milliseconds
    private void submit(String action, String query) {
        if (Utils.TextUtils.isEmpty(query)) {
            getNavigator().startSearching(action, query);
            if (handler != null) {
                if (runnable != null)
                    handler.removeCallbacks(runnable);
            }
            return;
        }

        if (handler != null) {
            if (runnable != null)
                handler.removeCallbacks(runnable);
        }
        runnable = new Runnable() {
            @Override
            public void run() {
                getNavigator().startSearching(action, query);
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, SUBMISSION_TIME);
    }

    private String query = "";

    @Override
    public void updateQuery(String query) {
        if (Utils.TextUtils.isEmpty(query)) {
            this.query = query;
            resultItems.set(new ArrayList<>());
            resultItems.notifyChange();
        }
        if (!this.query.equals(query)) {
            this.query = query;
            startSearching();
        }
    }

    private void startSearching() {
        List<Movie> movies = moviesData.get();
        if (movies == null || movies.isEmpty())
            return;

        interactor.searchMovies(movies, query, new InteractorCallback<List<DataItem>>() {
            @Override
            public void onSuccess(List<DataItem> sectionedList) {

                resultItems.set(sectionedList);

                resultItems.notifyChange();

                hideLoading();
            }

            @Override
            public void onFailed(Throwable errors) {

            }
        });
    }

    public ObservableField<List<Movie>> getMoviesData() {
        return moviesData;
    }

    public void setMoviesData(ObservableField<List<Movie>> moviesData) {
        this.moviesData = moviesData;
    }

    public ObservableField<List<DataItem>> getResultItems() {
        return resultItems;
    }

    public void setResultItems(ObservableField<List<DataItem>> resultItems) {
        this.resultItems = resultItems;
    }

    public MovieListAdapter getAdapter() {
        return adapter;
    }

    public SearchResultAdapter getSearchResultAdapter() {
        return searchResultAdapter;
    }

    public void setAdapter(MovieListAdapter adapter) {
        this.adapter = adapter;
    }

    public void setInteractor(MainInteractor interactor) {
        this.interactor = interactor;
    }

    public SearchView.OnQueryTextListener getOnQueryTextListener() {
        return onQueryTextListener;
    }

    public SearchView.OnCloseListener getOnCloseListener() {
        return onCloseListener;
    }

    public String getQuery() {
        return query;
    }
}
