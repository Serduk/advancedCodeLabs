package com.example.android.hilt

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.ui.MainActivity
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class AppTest {

    @Inject lateinit var db: LoggerLocalDataSource

    @After
    fun tearDown() {
        // Remove logs after the test finishes
        db.removeLogs()
    }

    @Test
    fun happyPath() {
        ActivityScenario.launch(MainActivity::class.java)

        // Check Buttons fragment screen is displayed
        onView(withId(R.id.textView)).check(matches(isDisplayed()))

        // Tap on Button 1
        onView(withId(R.id.button1)).perform(click())

        // Navigate to Logs screen
        onView(withId(R.id.all_logs)).perform(click())

        // Check Logs fragment screen is displayed
        onView(withText(containsString("Interaction with 'Button 1'")))
            .check(matches(isDisplayed()))
    }
}
