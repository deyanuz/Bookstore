package com.example.bookstoreapplication;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;
public class SignInActivityTest {

    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SignInActivity.class);

    @Test
    public void signInButtonClick() {
        Espresso.onView(ViewMatchers.withId(R.id.signInBtn)).perform(ViewActions.click());
        // Add assertions here to verify the behavior
    }
}
