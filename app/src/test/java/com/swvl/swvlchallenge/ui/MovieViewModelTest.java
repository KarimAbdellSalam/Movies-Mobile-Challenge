package com.swvl.swvlchallenge.ui;

import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.UIHelper;
import com.swvl.swvlchallenge.ui.movie.MovieInteractor;
import com.swvl.swvlchallenge.ui.movie.MovieViewModel;
import com.swvl.swvlchallenge.ui.rx.TestSchedulerProvider;
import com.swvl.swvlchallenge.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MovieViewModelTest {

    String movieQuery = "Mission Impossible";

    @Mock
    DataHelper dataHelper;

    @Mock
    UIHelper uiHelper;


    @Spy
    public MovieViewModel movieViewModel;

    private TestScheduler mTestScheduler;
    private MovieInteractor movieInteractor;
    private Movie movie;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        movieInteractor = new MovieInteractor(dataHelper, testSchedulerProvider);
//        movieViewModel = new MovieViewModel();
        movie = Movie.Mock.getMovie();
        movieViewModel.setMovie(movie);
        movieViewModel.setInteractor(movieInteractor);
        movieViewModel.setUIHelper(uiHelper);

    }

    @Test
    public void test_loading_flickr_photos() {
        FlickrResponse item = new FlickrResponse();
        item.setStat(Utils.Const.HTTP.OK);
        List<FlickrResponse.Photo> photos = getPhotos();

        when(dataHelper.searchImagesByMovieName(movie.getTitle(), 1, 300))
                .thenReturn(Single.just(item));

        when(dataHelper.filterMovies(item, movie.getTitle()))
                .thenReturn(Single.just(photos));

        movieViewModel.loadFlickrImages();
        mTestScheduler.triggerActions();
        verify(movieViewModel).filterPhotosByMovieName(item, movie);
        assertEquals(movieViewModel.getPhotos().get(), photos);
    }


    private List<FlickrResponse.Photo> getPhotos() {
        List<FlickrResponse.Photo> photos = new ArrayList<>();
        photos.add(new FlickrResponse.Photo());
        return photos;
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        movieViewModel = null;
    }

}
