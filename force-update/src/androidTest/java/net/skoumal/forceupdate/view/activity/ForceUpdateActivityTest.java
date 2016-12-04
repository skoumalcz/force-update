package net.skoumal.forceupdate.view.activity;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.skoumal.forceupdate.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ForceUpdateActivityTest {

    @Rule
    public ActivityTestRule<ForceUpdateActivity> mActivityRule = new ActivityTestRule<ForceUpdateActivity>(ForceUpdateActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            return super.getActivityIntent();
        }
    };

    @Test
    public void listGoesOverTheFold() {
        onView(withId(R.id.button)).check(matches(isDisplayed()));
    }
}
