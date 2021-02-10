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
        //Show the photos in app
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

        //Open Search Activity
        onView(withId(R.id.filter)).perform(click());
        //Type in Search criteria
        onView(withId(R.id.latitudeid)).perform(replaceText("80"), closeSoftKeyboard());
        onView(withId(R.id.longitudeid)).perform(replaceText("-110"), closeSoftKeyboard());
        onView(withId(R.id.latitudeid2)).perform(replaceText("90"), closeSoftKeyboard());
        onView(withId(R.id.longitudeid2)).perform(replaceText("-90"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());

        //check to see if any other photos show up
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());
        onView(withId(R.id.previd)).perform(click());

    }
}

