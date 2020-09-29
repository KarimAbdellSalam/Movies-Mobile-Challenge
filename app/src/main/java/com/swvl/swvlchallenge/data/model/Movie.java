package com.swvl.swvlchallenge.data.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class Movie extends BaseObservable implements Serializable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("cast")
    @Expose
    private List<String> cast = null;
    @SerializedName("genres")
    @Expose
    private List<String> genres = null;
    @SerializedName("rating")
    @Expose
    private float rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyChange();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
        notifyChange();
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
        notifyChange();
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
        notifyChange();
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
        notifyChange();
    }

    public String getGenresString() {
        if (genres == null || genres.isEmpty())
            return "";
        StringBuilder builder = new StringBuilder(genres.get(0));
        for (int i = 1; i < genres.size(); i++) {
            builder.append(" / ").append(genres.get(i));
        }
        return builder.toString();
    }

    public String getCastString() {
        if (cast == null || cast.isEmpty())
            return "";
        StringBuilder builder = new StringBuilder(cast.get(0));
        for (int i = 1; i < cast.size(); i++) {
            builder.append("  ").append(cast.get(i));
        }
        return builder.toString();
    }

    public static class Mock {
        @NotNull
        public static List<Movie> getMovies() {
            List<Movie> movies = new ArrayList<>();
            movies.add(getMovie());
            movies.add(getMovie());
            movies.add(getMovie());
            movies.add(getMovie());
            return movies;
        }

        @NotNull
        public static Movie getMovie() {
            int arr[] = {2002, 2004, 2005, 2008, 2009, 2011, 2012};
            String genres[] = {"Romance",
                    "Comedy", "Action"};
            String cast[] = {"John Cena",
                    "Ashley Scott",
                    "Steve Harris",
                    "Aidan Gillen",
                    "Brian J. White",
                    "Taylor Cole"};
            Movie movie = new Movie();
            movie.setTitle("Test Movie");
            movie.setYear(arr[new Random().nextInt(arr.length)]);
            movie.setRating(1 + new Random().nextFloat() * (5 - 1));
            movie.setGenres(Arrays.asList(genres));
            movie.setCast(Arrays.asList(cast));
            return movie;
        }
    }
}
