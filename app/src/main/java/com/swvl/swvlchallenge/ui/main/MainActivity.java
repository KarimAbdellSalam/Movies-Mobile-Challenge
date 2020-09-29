package com.swvl.swvlchallenge.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swvl.swvlchallenge.BR;
import com.swvl.swvlchallenge.R;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.databinding.ActivityMainBinding;
import com.swvl.swvlchallenge.manager.ScreenControl;
import com.swvl.swvlchallenge.ui.base.BaseActivity;
import com.swvl.swvlchallenge.utils.Utils;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel> implements MainNavigator {

    @Inject
    MainViewModel viewModel;

    private ActivityMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        RecyclerView recyclerView = ((ActivityMainBinding) getViewDataBinding()).mainActRc;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        RecyclerView searchResultsRc = ((ActivityMainBinding) getViewDataBinding()).mainActSearchResultRc;
        searchResultsRc.setLayoutManager(new LinearLayoutManager(this));
        searchResultsRc.setHasFixedSize(true);
        searchResultsRc.setNestedScrollingEnabled(false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityMainBinding) getViewDataBinding();
        viewModel.setNavigator(this);
        viewModel.loadData();
    }

    @Override
    public void startSearching(String action, String query) {
        Intent searchIntent = new Intent(this, MainActivity.class);
        searchIntent.setAction(action);
        searchIntent.putExtra(SearchManager.QUERY, query);
        startActivity(searchIntent);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            queryChanged(query);
        } else if (Utils.Const.Action.END_SEARCH.equals(intent.getAction())) {
            queryChanged("");
        }
    }

    private void queryChanged(String query) {
        viewModel.updateQuery(query);
    }

    @Override
    public void openMovieActivity(Movie movie) {
        ScreenControl.openMovieActivity(this, movie);
    }
}