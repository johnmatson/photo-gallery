package com.example.photoapp;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void ensureTextChangesWork() {
        //Automated UI Test for search Activity
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.etlongitude)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.etlongitude)).check(matches(withText("")));

        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.etlatitude)).perform(replaceText(""), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.etlatitude)).check(matches(withText("")));

        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

    }
}

