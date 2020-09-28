package com.swvl.swvlchallenge.ui;

import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.main.IMainViewModel;
import com.swvl.swvlchallenge.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.TestScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class MainViewModelTest {
    @Mock
    IMainViewModel mViewModel;

    private MainViewModel mainViewModel;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mainViewModel = new MainViewModel();
    }

    @Test
    public void test_loading_movies() {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movies.add(movie);
    }
}
