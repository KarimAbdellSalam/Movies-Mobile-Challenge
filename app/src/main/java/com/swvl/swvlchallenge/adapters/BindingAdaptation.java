package com.swvl.swvlchallenge.adapters;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.main.MovieListAdapter;
import com.swvl.swvlchallenge.ui.main.SearchResultAdapter;
import com.swvl.swvlchallenge.ui.movie.ImagesListAdapter;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class BindingAdaptation {

    @BindingAdapter({"imageUrl", "cacheEnabled"})
    public static void setImageUrl(ImageView imageView, String url, boolean cache) {
        Context context = imageView.getContext();
        if (!TextUtils.isEmpty(url))
            Glide.with(context)
                    .load(url)
                    .apply(cache ? RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL) : RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(imageView);
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        if (!TextUtils.isEmpty(url))
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(imageView);
    }

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

    @BindingAdapter({"app:adapter", "app:dataItems"})
    public static void bindMovieImages(RecyclerView recyclerView, ImagesListAdapter adapter, List<FlickrResponse.Photo> list) {
        recyclerView.setAdapter(adapter);
        adapter.updateItems(list);
    }
}
