package com.example.photoapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class InstrumentedTest{

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    
    
    
    
    @Test
    public void Sprint1Test() {
        //Automated UI Test for search Activity

        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.etStartDateTime)).perform(replaceText("20210120 000000"), closeSoftKeyboard());
        onView(withId(R.id.etEndDateTime)).perform(replaceText("20210126 000000"), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("Potato"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.captionid)).check(matches(withText("Potato")));

        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

    }
    @Test
    public void Sprint2Test() throws InterruptedException {

        // Automated Share
        onView(withId(R.id.shareid)).perform(click());
        assertTrue(mDevice.pressBack());
    }


}

