package com.example.earthypalettegenerator;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void buttonGeneratesNewPalette() {

        final String[] initialHex = new String[1];
        final String[] updatedHex = new String[1];

        // Read initial text
        activityRule.getScenario().onActivity(activity -> {
            TextView label1 = activity.findViewById(R.id.label1);
            initialHex[0] = label1.getText().toString();
        });

        // Trigger button click directly
        activityRule.getScenario().onActivity(activity -> {
            activity.findViewById(R.id.generateButton).performClick();
        });

        // Read text again
        activityRule.getScenario().onActivity(activity -> {
            TextView label1 = activity.findViewById(R.id.label1);
            updatedHex[0] = label1.getText().toString();
        });

        // Assert change using JUnit
        org.junit.Assert.assertNotEquals(initialHex[0], updatedHex[0]);
    }

}
