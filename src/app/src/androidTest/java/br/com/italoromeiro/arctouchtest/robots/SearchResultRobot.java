package br.com.italoromeiro.arctouchtest.robots;

import br.com.italoromeiro.arctouchtest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by italo on 13/08/16.
 */
public class SearchResultRobot {
    public SearchResultRobot isSuccessWithManyResults() {
        onView(withId(R.id.tv_amount)).check(matches(isDisplayed()));
        onView(withText("2 routes found")).check(matches(isDisplayed()));
        onView(withId(R.id.rv_routes)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_container)).check(matches(not(isDisplayed())));
        return this;
    }

    public SearchResultRobot isSuccessWithOneResult() {
        onView(withId(R.id.tv_amount)).check(matches(isDisplayed()));
        onView(withText("1 route found")).check(matches(isDisplayed()));
        onView(withId(R.id.rv_routes)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_container)).check(matches(not(isDisplayed())));
        return this;
    }

    public SearchResultRobot isSuccessWithoutResults() {
        onView(withId(R.id.tv_amount)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_amount)).check(matches(isDisplayed()));
        onView(withText(R.string.textview_label_route_empty)).check(matches(isDisplayed()));
        return this;
    }

    public SearchResultRobot isFail() {
        onView(withText("We couldn\'t get info at this time. Please try again.")).check(matches(isDisplayed()));
        return this;
    }
}
