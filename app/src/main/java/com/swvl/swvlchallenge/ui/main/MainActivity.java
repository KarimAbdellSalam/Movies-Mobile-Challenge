package com.swvl.swvlchallenge.ui.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swvl.swvlchallenge.BR;
import com.swvl.swvlchallenge.R;
import com.swvl.swvlchallenge.databinding.ActivityMainBinding;
import com.swvl.swvlchallenge.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel> {

    @Inject
    MainViewModel viewModel;

    private ActivityMainBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void init() {
        RecyclerView recyclerView = ((ActivityMainBinding) getViewDataBinding()).movieActRc;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
        viewModel.loadData();
    }
}