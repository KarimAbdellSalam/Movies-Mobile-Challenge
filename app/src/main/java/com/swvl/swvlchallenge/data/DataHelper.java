package com.swvl.swvlchallenge.data;

import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.data.remote.ApiHelper;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public interface DataHelper extends ApiHelper {
    public Observable<List<Movie>> loadAllMovies();

    Observable<List<DataItem>> searchMovies(List<Movie> movies, String query);

    @NotNull
    List<Movie> getMoviesByTitle(List<Movie> movies, String query);

    // create hash map contains movies categories by by category < year , list of movies>
    Map<Integer, List<Movie>> createCategorizedMovies(List<Movie> movies);

    // sort categories descending
    @NotNull
    LinkedHashMap<Integer, List<Movie>> getSortedCategoriesMovies(Map<Integer, List<Movie>> movies);

    @NotNull
    List<Movie> getTopRatedMovies(List<Movie> movies, int maxItems);

    @NotNull
    List<DataItem> getSectionedList(LinkedHashMap<Integer, List<Movie>> sortedLinkedMap);
}
