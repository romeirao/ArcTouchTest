package br.com.italoromeiro.arctouchtest.robots;

import br.com.italoromeiro.arctouchtest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by italo on 13/08/16.
 */
public class SearchResultRobot {
    public SearchResultRobot isSuccess() {
        onView(withId(R.id.rv_routes)).check(matches(isDisplayed()));
        return this;
    }
}
