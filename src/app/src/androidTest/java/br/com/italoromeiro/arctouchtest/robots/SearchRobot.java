package br.com.italoromeiro.arctouchtest.robots;

import br.com.italoromeiro.arctouchtest.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by italo on 13/08/16.
 */
public class SearchRobot {
    public SearchRobot where(String nameOfStreet) {
        onView(withId(R.id.et_search)).perform(typeText(nameOfStreet), closeSoftKeyboard());
        return this;
    }

    public SearchResultRobot search() {
        onView(withId(R.id.btn_search)).perform(click());
        return new SearchResultRobot();
    }
}
