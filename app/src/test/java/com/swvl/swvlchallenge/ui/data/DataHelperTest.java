package com.swvl.swvlchallenge.ui.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.swvl.swvlchallenge.data.Repository;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.data.model.MoviesJsonFile;
import com.swvl.swvlchallenge.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Karim Abdell Salam on 30,September,2020
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class DataHelperTest {

    Repository repository;


    private MockWebServer mockWebServer = new MockWebServer();
    private Retrofit retrofit;


    @Before
    public void setup() throws Exception {
        mockWebServer.start();
        retrofit = FactoryForTest.Network.buildRetrofit(mockWebServer);
        repository = new Repository(retrofit, null, FactoryForTest.Converters.provideGson());
    }


    @Test
    public void test_flickr_search_movies() {
        String body = Utils.Data.loadJsonFileFromResources(getClass(), "flickr_response.json");
        MockResponse response = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(body);
        mockWebServer.enqueue(response);
        @NonNull FlickrResponse flickrResponse = repository.searchImagesByMovieName("Mission Impossible", 1, 300).blockingGet();
        assertNotNull(flickrResponse);
        assertNotEquals(flickrResponse.getPhotos().getPhoto().size(), 0);
        assertEquals(flickrResponse.getPhotos().getPerpage().intValue(), 300);
        assertEquals(flickrResponse.getStat(), Utils.Const.HTTP.OK);
    }

    @Test
    public void test_flickr_filter_photos() {
        String body = Utils.Data.loadJsonFileFromResources(getClass(), "flickr_response.json");
        MockResponse response = new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(body);
        mockWebServer.enqueue(response);
        @NonNull FlickrResponse flickrResponse = repository.searchImagesByMovieName("Mission Impossible", 1, 300).blockingGet();
        assertEquals(flickrResponse.getStat(), Utils.Const.HTTP.OK);
        repository.filterMovies(flickrResponse, "Magic Kingdom fun").test().dispose();
    }

    @Test
    public void test_search_movies_by_query() {
        MoviesJsonFile movie = getMoviesJsonFile();
        assertNotEquals(movie.getMovies().size(), 0);
        assertNotEquals(repository.getMoviesByTitle(movie.getMovies(), "Mission"), 0);
        Movie mission = repository.getMoviesByTitle(movie.getMovies(), "Mission").get(0);
        assertNotEquals(mission.getTitle().contains("Mission"), false);
        assertEquals(repository.getMoviesByTitle(movie.getMovies(), "jsdklfjdslkjf").size(), 0);
    }

    @Test
    public void test_create_movies_mapped_by_year() {
        Map<Integer, List<Movie>> categorizedMovies = getIntegerListMap();
        assertNotNull(categorizedMovies);
        assertEquals(categorizedMovies.size(), 10); // 10 sections
        assertNotEquals(categorizedMovies.size(), 0);
        assertNotNull(categorizedMovies.get(2018));
        assertEquals(categorizedMovies.get(2010).size(), 210);
    }

    @Test
    public void test_create_movies_sorted_mapped_by_year() {
        LinkedHashMap<Integer, List<Movie>> sortedCategoriesMovies = repository.getSortedCategoriesMovies(getIntegerListMap());
        assertNotNull(sortedCategoriesMovies);
        assertEquals(sortedCategoriesMovies.entrySet().size(), 10); // 10 sections
    }

    @Test
    public void test_get_top_rated_movies() {
        List<Movie> topRatedMovies = repository.getTopRatedMovies(getMoviesJsonFile().getMovies(), 10);
        assertNotNull(topRatedMovies);
        assertEquals(topRatedMovies.size(), 10);
        assertEquals(topRatedMovies.get(0).getRating() >= topRatedMovies.get(1).getRating(), true);
        assertEquals(repository.getTopRatedMovies(getMoviesJsonFile().getMovies(), 5).size(), 5); 
    }

    public Map<Integer, List<Movie>> getIntegerListMap() {
        return repository.createCategorizedMovies(getMoviesJsonFile().getMovies());
    }

    public MoviesJsonFile getMoviesJsonFile() {
        String body = Utils.Data.loadJsonFileFromResources(getClass(), "movies.json");
        Type type = new TypeToken<MoviesJsonFile>() {
        }.getType();
        return new Gson().fromJson(body, type);
    }

    @After
    public void teardown() {
        retrofit = null;
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
