package com.swvl.swvlchallenge.ui.main;


import androidx.databinding.ObservableField;

import com.swvl.swvlchallenge.callbacks.OnInteractionListener;
import com.swvl.swvlchallenge.data.model.Movie;


public class MovieItemViewModel {
    private Movie item;
    private OnInteractionListener mListener;

    public MovieItemViewModel(Movie item, OnInteractionListener mListener) {
        this.mListener = mListener;
        this.item = item;
    }

    private ObservableField<String> getObservableString(String field) {
        return new ObservableField<>(field);
    }

    public void onItemClick() {
        mListener.onItemClicked(item);
    }

    public OnInteractionListener getmListener() {
        return mListener;
    }

    public Movie getMovie() {
        return item;
    }

    public void setItem(Movie item) {
        this.item = item;
    }


}