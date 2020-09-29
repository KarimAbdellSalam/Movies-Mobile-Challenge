package com.swvl.swvlchallenge.ui;

import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.base.UIHelper;
import com.swvl.swvlchallenge.ui.main.IMainViewModel;
import com.swvl.swvlchallenge.ui.main.MainInteractor;
import com.swvl.swvlchallenge.ui.main.MainViewModel;
import com.swvl.swvlchallenge.ui.rx.TestSchedulerProvider;

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
        MainInteractor mainInteractor = new MainInteractor(dataHelper,testSchedulerProvider);
        mainViewModel = new MainViewModel();
        mainViewModel.setInteractor(mainInteractor);
        mainViewModel.setUIHelper(uiHelper);

    }

    @Test
    public void test_loading_movies() {
        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movies.add(movie);
        assertEquals(mainViewModel.getMoviesData().get().size(),0);

        doReturn(Observable.just(movies))
                .when(dataHelper)
                .loadAllMovies();

        mainViewModel.loadData();
        mTestScheduler.triggerActions();
        verify(uiHelper,atLeast(1)).showLoading();
        verify(uiHelper, atLeast(1)).hideLoading();
        assertNotNull(mainViewModel.getMoviesData().get());
        assertEquals(mainViewModel.getMoviesData().get().getClass(),movies.getClass());
        assertNotEquals(mainViewModel.getMoviesData().get().size(),0);

    }
}
