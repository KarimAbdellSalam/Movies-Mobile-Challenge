package com.swvl.swvlchallenge.ui.movie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.swvl.swvlchallenge.BR;
import com.swvl.swvlchallenge.R;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.databinding.ActivityMovieBinding;
import com.swvl.swvlchallenge.manager.GridSpacingItemDecoration;
import com.swvl.swvlchallenge.ui.base.templates.ActivityWithBack;
import com.swvl.swvlchallenge.utils.Utils;

import javax.inject.Inject;

public class MovieActivity extends ActivityWithBack<MovieViewModel> {


    public ActivityMovieBinding binding;
    @Inject
    MovieViewModel viewModel;
    private Movie movie;

    public static Intent newIntent(Context context) {
        return new Intent(context, MovieActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    public MovieViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Utils.Const.Ref.MOVIE)) {
            movie = (Movie) extras.getSerializable(Utils.Const.Ref.MOVIE);
        }
        super.onCreate(savedInstanceState);
        binding = (ActivityMovieBinding) getViewDataBinding();
        viewModel.setMovie(movie);
        viewModel.loadFlickrImages();
    }

    @Override
    public void init() {
        super.init();
        int spacing = Utils.Screen.dpToPx(16); // 50px
        int spanCount = 2; // 2 columns
        boolean includeEdge = true;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);

        RecyclerView recyclerView = ((ActivityMovieBinding) getViewDataBinding()).modelActRc;
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        //-----

    }

    @Override
    protected int getToolbarOptionMenuId() {
        return -1;
    }

    @Override
    protected int getUpIconId() {
        return 0;
    }

    @Override
    public String getScreenTitle() {
        return movie.getTitle();
    }

    @Override
    public Drawable getScreenLogo() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}