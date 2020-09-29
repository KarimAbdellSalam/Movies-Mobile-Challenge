package com.swvl.swvlchallenge.adapters;


import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.main.MovieListAdapter;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class BindingAdaptation {

    @BindingAdapter({"app:adapter", "app:Data"})
    public static void bindMovies(RecyclerView recyclerView, MovieListAdapter adapter, List<Movie> list) {
        recyclerView.setAdapter(adapter);
        adapter.updateItems(list);
    }
}
