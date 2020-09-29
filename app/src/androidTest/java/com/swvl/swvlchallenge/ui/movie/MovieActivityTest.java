package com.swvl.swvlchallenge.ui.main.movie;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.swvl.swvlchallenge.R;
import com.swvl.swvlchallenge.data.model.Movie;
import com.swvl.swvlchallenge.ui.movie.MovieActivity;
import com.swvl.swvlchallenge.utils.Utils;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
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
        onView(withId(R.id.movieAct_title))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_rating))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_year))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_genres))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_castLabel))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_cast))
                .check(matches(isDisplayed()));
        onView(withId(R.id.movieAct_Rc))
                .check(matches(isDisplayed()));
    }
    @Test
    public void checkViewsDataValues() {
        activityTestRule.launchActivity(null);

        onView(withId(R.id.movieAct_title))
                .check(matches(withText(movie.getTitle())));
        onView(withId(R.id.movieAct_rating))
                .check(matches(withText(movie.getRating()+"")));
        onView(withId(R.id.movieAct_year))
                .check(matches(withText(movie.getYear().toString())));
        onView(withId(R.id.movieAct_genres))
                .check(matches(withText(movie.getGenresString())));
        onView(withId(R.id.movieAct_castLabel))
                .check(matches(withText("Cast")));
        onView(withId(R.id.movieAct_cast))
                .check(matches(withText(movie.getCastString())));

    }

}
