package com.swvl.swvlchallenge.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.swvl.swvlchallenge.BuildConfig;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.data.model.MoviesJsonFile;
import com.swvl.swvlchallenge.data.remote.ProjectApi;
import com.swvl.swvlchallenge.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class Repository implements DataHelper {
    private Gson gson;
    Context mContext;
    ProjectApi.FlickerApi flickerApi;
    @Inject
    public Repository(Retrofit retrofit, Context mContext, Gson gson) {
        flickerApi = retrofit.create(ProjectApi.FlickerApi.class);
        this.mContext = mContext;
        this.gson = gson;
    }

    @Override
    public Observable<List<Movie>> loadAllMovies() {
        return Observable.fromCallable(new Callable<List<Movie>>() {
            @Override
            public List<Movie> call() throws Exception {
                Type type = new TypeToken<MoviesJsonFile>() {
                }.getType();
                MoviesJsonFile moviesJsonFile = gson.fromJson(Utils.Data.loadJSONFromAsset(mContext, Utils.Const.SEED_MOVIE_JSON), type);
                return moviesJsonFile.getMovies();
            }
        });
    }

    @Override
    public Observable<List<DataItem>> searchMovies(List<Movie> movies, String query) {
        return Observable.fromCallable(new Callable<List<DataItem>>() {
            @Override
            public List<DataItem> call() throws Exception {
                List<Movie> filteredMovies = getMoviesByTitle(movies, query);

                Map<Integer, List<Movie>> categorizedMovies = createCategorizedMovies(filteredMovies);

                LinkedHashMap<Integer, List<Movie>> sortedCategoriesMovies = getSortedCategoriesMovies(categorizedMovies);

                List<DataItem> sectionedList = getSectionedList(sortedCategoriesMovies);
                return sectionedList;
            }
        });
    }

    @NotNull
    @Override
    public List<Movie> getMoviesByTitle(List<Movie> movies, String query) {
        return movies.stream()
                .parallel()
                .filter(movie -> Utils.TextUtils.removeUnnecessaryCharacters(movie.getTitle()) // used to ignore unnecessary characters to improve searching result
                        .toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    // create hash map contains movies categories by by category < year , list of movies>
    @Override
    public Map<Integer, List<Movie>> createCategorizedMovies(List<Movie> movies) {
        return movies.stream()
                .parallel()
                .collect(groupingBy(Movie::getYear));
    }

    // sort categories descending
    @NotNull
    @Override
    public LinkedHashMap<Integer, List<Movie>> getSortedCategoriesMovies(Map<Integer, List<Movie>> movies) {
        return movies.entrySet().stream()
                .parallel()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @NotNull
    @Override

    public List<Movie> getTopRatedMovies(List<Movie> movies, int maxItems) {
        return movies.stream()
                .parallel()
                .sorted((a1, a2) -> a2.getRating()
                        .compareTo(a1.getRating()))
                .limit(maxItems)
                .collect(Collectors.toList());
    }

    // build a list of DataItem class which used in sectioned recycler "by year"
    //todo need to be enhanced
    @NotNull
    @Override
    public List<DataItem> getSectionedList(LinkedHashMap<Integer, List<Movie>> sortedLinkedMap) {
        List<DataItem> dataItems = new ArrayList<>();

        for (Map.Entry<Integer, List<Movie>> category : sortedLinkedMap.entrySet()) {
            dataItems.add(new DataItem.Header(category.getKey() + ""));
            List<Movie> movies = sortedLinkedMap.get(category.getKey());
            assert movies != null;
            int MAX_TOP_RATED_NUMBER = 5;
            for (Movie movie : getTopRatedMovies(movies, MAX_TOP_RATED_NUMBER) // top rated five movies
            ) {
                dataItems.add(new DataItem.MovieItem(movie));
            }
        }
        return dataItems;
    }

    @Override
    public Single<FlickrResponse> searchImagesByMovieName(String movieName, int page, int perPage) {
        // remove unnecessary characters which may improve the flickr search results
        String searchQuery = Utils.TextUtils.removeUnnecessaryCharacters(movieName);
        return flickerApi.searchMovieImages(Utils.Const.Ref.FLICKR_METHOD, BuildConfig.FLICKR_API_KEY, "json", 1, searchQuery, page, perPage);
    }

    @Override
    public Single<List<FlickrResponse.Photo>> filterMovies(FlickrResponse flickrResponse, String title) {
        return Single.fromCallable(new Callable<List<FlickrResponse.Photo>>() {
            @Override
            public List<FlickrResponse.Photo> call() throws Exception {
                return flickrResponse
                        .getPhotos()
                        .getPhoto().stream()
                        .parallel()
                        .filter(movie -> movie.getTitle()
                                .equals(title))
                        .collect(Collectors.toList());
            }
        });
    }
}
