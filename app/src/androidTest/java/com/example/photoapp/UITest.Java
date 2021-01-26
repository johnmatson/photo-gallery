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
        //the following code needs to be renamed to work with this app's component names

        //Espresso.onView(withId(R.id.editText)).perform(ViewActions.typeText("HELLO"), ViewActions.closeSoftKeyboard());
        //Espresso.onView(withId(R.id.button)).perform(ViewActions.click());
        //Espresso.onView(withId(R.id.textView)).check(ViewAssertions.matches(ViewMatchers.withText("HELLO")));

        //What needs to be automated and tested
        //Test taking a photo
        //Test making sure photo has been saved with name
        //Test Left and Right buttons to go through photos
        //Test Search by date functionality.


        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.etStartDateTime)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etEndDateTime)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.etKeywords)).perform(typeText("caption"), closeSoftKeyboard());
        onView(withId(R.id.go)).perform(click());
        onView(withId(R.id.captionid)).check(matches(withText("caption")));
        onView(withId(R.id.nextid)).perform(click());
        onView(withId(R.id.previd)).perform(click());


    }
}

