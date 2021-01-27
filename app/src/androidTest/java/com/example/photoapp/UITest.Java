package com.example.photoapp;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



public class UITest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void ensureTextChangesWork() {
        //Automated UI Test for search Activity


        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.etStartDateTime)).perform(typeText("20210120 000000"), closeSoftKeyboard());
        onView(withId(R.id.etEndDateTime)).perform(typeText("20210126 000000"), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("Potato"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.captionid)).check(matches(withText("Potato")));
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());



    }
}

