package com.swvl.swvlchallenge.ui;

import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.DataItem;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.UIHelper;
import com.swvl.swvlchallenge.ui.main.IMainViewModel;
import com.swvl.swvlchallenge.ui.main.MainInteractor;
import com.swvl.swvlchallenge.ui.main.MainViewModel;
import com.swvl.swvlchallenge.ui.rx.TestSchedulerProvider;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MainViewModelTest {
    @Mock
    IMainViewModel mViewModel;

    @Mock
    DataHelper dataHelper;

    @Mock
    UIHelper uiHelper;

    private MainViewModel mainViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        MainInteractor mainInteractor = new MainInteractor(dataHelper, testSchedulerProvider);
        mainViewModel = new MainViewModel();
        mainViewModel.setInteractor(mainInteractor);
        mainViewModel.setUIHelper(uiHelper);

    }

    @Test
    public void test_loading_movies() {
        List<Movie> movies = getMovies();
        assertEquals(mainViewModel.getMoviesData().get().size(), 0);

        doReturn(Observable.just(movies))
                .when(dataHelper)
                .loadAllMovies();

        mainViewModel.loadData();
        mTestScheduler.triggerActions();
        verify(uiHelper, atLeast(1)).showLoading();
        verify(uiHelper, atLeast(1)).hideLoading();
        assertNotNull(mainViewModel.getMoviesData().get());
        assertEquals(mainViewModel.getMoviesData().get().getClass(), movies.getClass());
        assertNotEquals(mainViewModel.getMoviesData().get().size(), 0);
    }

    @Test
    public void test_search_movies() {

        List<Movie> movies = getMovies();
        List<DataItem> dataItems = new ArrayList<>();
        DataItem dataItemHeader = new DataItem();
        DataItem dataItemMovies = new DataItem();
        dataItemHeader.setHeaderItem(new DataItem.Header("2012"));
        dataItemMovies.setMovieItem(new DataItem.MovieItem(getMovie()));
        dataItems.add(dataItemHeader);
        dataItems.add(dataItemMovies);
        dataItems.add(dataItemMovies);
        dataItems.add(dataItemMovies);
        dataItems.add(dataItemMovies);
        dataItems.add(dataItemMovies);

        doReturn(Observable.just(dataItems))
                .when(dataHelper)
                .searchMovies(movies, "te");

        mainViewModel.getMoviesData().set(movies);
        mainViewModel.updateQuery("te");
        mTestScheduler.triggerActions();

        assertNotNull(mainViewModel.getResultItems().get());


        assertEquals(mainViewModel.getResultItems().get().getClass(), dataItems.getClass());
        assertEquals(mainViewModel.getResultItems().get(), dataItems);
    }


    @NotNull
    private List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(getMovie());
        movies.add(getMovie());
        movies.add(getMovie());
        movies.add(getMovie());
        return movies;
    }

    @NotNull
    private Movie getMovie() {
        int arr[] = {2002, 2004, 2005, 2008, 2009, 2011, 2012};
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setYear(arr[new Random().nextInt(arr.length)]);
        movie.setRating(1 + new Random().nextFloat() * (5 - 1));
        return movie;
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        mainViewModel = null;
    }

}
