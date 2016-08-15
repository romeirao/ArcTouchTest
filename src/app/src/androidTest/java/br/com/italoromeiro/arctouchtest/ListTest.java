package br.com.italoromeiro.arctouchtest;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.italoromeiro.arctouchtest.core.TestConstants;
import br.com.italoromeiro.arctouchtest.rest.MockRestController;
import br.com.italoromeiro.arctouchtest.robots.SearchResultRobot;
import br.com.italoromeiro.arctouchtest.robots.SearchRobot;
import br.com.italoromeiro.arctouchtest.views.activities.ListActivity;
import br.com.italoromeiro.arctouchtest.views.activities.ListActivity_;

/**
 * Created by italo on 13/08/16.
 */
@RunWith(AndroidJUnit4.class)
public class ListTest {

    @Rule
    public ActivityTestRule<ListActivity_> mActivityRule = new ActivityTestRule(ListActivity_.class, false, false);

    private ListActivity mActivity;

    @Before
    public void setUp() {
        mActivityRule.launchActivity(new Intent());

        mActivity = mActivityRule.getActivity();
        MockRestController api = new MockRestController(mActivity);
        mActivity.getPresenter().setRestController(api);
    }

    @Test
    public void searchSuccessWithManyResults() throws Exception {
        SearchRobot searchRobot = new SearchRobot();

        SearchResultRobot searchResultRobot = searchRobot
                .where(TestConstants.STOP_NAME_GOOD_WITH_MANY_RESULTS)
                .search();

        searchResultRobot.isSuccessWithManyResults();
    }

    @Test
    public void searchSuccessWithOneResult() throws Exception {
        SearchRobot searchRobot = new SearchRobot();

        SearchResultRobot searchResultRobot = searchRobot
                .where(TestConstants.STOP_NAME_GOOD_WITH_ONE_RESULT)
                .search();

        searchResultRobot.isSuccessWithOneResult();
    }

    @Test
    public void searchSuccessWithoutResults() throws Exception {
        SearchRobot searchRobot = new SearchRobot();

        SearchResultRobot searchResultRobot = searchRobot
                .where(TestConstants.STOP_NAME_GOOD_WITHOUT_RESULTS)
                .search();

        searchResultRobot.isSuccessWithoutResults();
    }

    @Test
    public void searchFail() throws Exception {
        SearchRobot searchRobot = new SearchRobot();

        SearchResultRobot searchResultRobot = searchRobot
                .where(TestConstants.STOP_NAME_FAIL)
                .search();

        searchResultRobot.isFail();
    }
}
