package com.swvl.swvlchallenge.ui.main.movie;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.swvl.swvlchallenge.R;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.movie.MovieActivity;
import com.swvl.swvlchallenge.utils.Utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */

@RunWith(AndroidJUnit4.class)
public class MovieActivityTest {
    Movie movie = Movie.Mock.getMovie();

    @Rule
    public ActivityTestRule<MovieActivity> activityTestRule =
            new ActivityTestRule<MovieActivity>(MovieActivity.class, true, false /*lazy launch activity*/) {

                @Override
                protected Intent getActivityIntent() {
                    /*added predefined intent data*/
                    Intent intent = new Intent();
                    intent.putExtra(Utils.Const.Ref.MOVIE, movie);
                    return intent;
                }
            };

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.swvl.swvlchallenge", appContext.getPackageName());
    }

    @Test
    public void checkViewsDisplay() {
        activityTestRule.launchActivity(null);
        onView(withId(R.id.movieAct_loadingPr))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_titleTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_ratingTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_yearTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_genresTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_castLabelTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_castTv))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_Rc))
                .check(matches(isDisplayed()));
    }
    @Test
    public void checkViewsDataValues() {
        activityTestRule.launchActivity(null);

        onView(withId(R.id.movieAct_titleTv))
                .check(matches(withText(movie.getTitle())));
        onView(withId(R.id.movieAct_ratingTv))
                .check(matches(withText(movie.getRating()+"")));
        onView(withId(R.id.movieAct_yearTv))
                .check(matches(withText(movie.getYear().toString())));
        onView(withId(R.id.movieAct_genresTv))
                .check(matches(withText(movie.getGenresString())));
        onView(withId(R.id.movieAct_castLabelTv))
                .check(matches(withText("Cast")));
        onView(withId(R.id.movieAct_castTv))
                .check(matches(withText(movie.getCastString())));

    }

}
