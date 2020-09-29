package com.swvl.swvlchallenge.data.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class MoviesJsonFile extends BaseObservable implements Serializable {
    @SerializedName("movies")
    @Expose
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
