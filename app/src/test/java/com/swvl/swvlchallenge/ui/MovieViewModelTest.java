package com.swvl.swvlchallenge.ui;

import com.swvl.swvlchallenge.data.DataHelper;
import com.swvl.swvlchallenge.data.model.FlickrResponse;
import com.swvl.swvlchallenge.ui.base.UIHelper;
import com.swvl.swvlchallenge.ui.movie.MovieViewModel;
import com.swvl.swvlchallenge.ui.rx.TestSchedulerProvider;

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
public class MovieViewModelTest {

    @Mock
    DataHelper dataHelper;

    @Mock
    UIHelper uiHelper;

    private MovieViewModel movieViewModel;
    private TestScheduler mTestScheduler;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        //  MovieInteractor mainInteractor = new MovieInteractor(dataHelper, testSchedulerProvider);
        movieViewModel = new MovieViewModel();
        //movieViewModel.setInteractor(mainInteractor);
        movieViewModel.setUIHelper(uiHelper);

    }

    @Test
    public void test_loading_flickr_photos() {
        List<FlickrResponse.Photo> photos = getPhotos();

        assertEquals(movieViewModel.getPhotos().get().size(), 0);

        doReturn(Observable.just(photos))
                .when(dataHelper)
                .searchImagesByMovieName("Mission Impossible",1,400);

        movieViewModel.loadFlickrImages();
        mTestScheduler.triggerActions();

        verify(uiHelper, atLeast(1)).showLoading();
        verify(uiHelper, atLeast(1)).hideLoading();

    }


    private List<FlickrResponse.Photo> getPhotos() {
        return new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        mTestScheduler = null;
        movieViewModel = null;
    }

}
