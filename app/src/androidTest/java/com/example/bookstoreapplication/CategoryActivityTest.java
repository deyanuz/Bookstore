package com.example.bookstoreapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CategoryActivityTest {

    @Rule
    public ActivityScenarioRule<CategoryActivity> activityRule =
            new ActivityScenarioRule<>(CategoryActivity.class);

    @Test
    public void testWelcomeMessage() {
        Espresso.onView(withId(R.id.Welcome))
                .check(matches(withText("Welcome!")));
    }

    @Test
    public void testBookLanguageSelection() {
        // Click on English button and check if BooksNovels layout is visible
        Espresso.onView(withId(R.id.English)).perform(click());
        Espresso.onView(withId(R.id.BooksNovels))
                .check(matches(isDisplayed()));
    }

}
