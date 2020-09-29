package com.swvl.swvlchallenge.adapters;


import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.main.MovieListAdapter;
import com.swvl.swvlchallenge.ui.main.SearchResultAdapter;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class BindingAdaptation {

    @BindingAdapter({"app:adapter", "app:dataItems"})
    public static void bindMovies(RecyclerView recyclerView, MovieListAdapter adapter, List<Movie> list) {
        recyclerView.setAdapter(adapter);
        adapter.updateItems(list);
    }
    @BindingAdapter({"app:adapter", "app:dataItems"})
    public static void bindMovies(RecyclerView recyclerView, SearchResultAdapter adapter, List<DataItem> list) {
        recyclerView.setAdapter(adapter);
        adapter.updateItems(list);
    }
}
