package com.flknlabs.uitestexample

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.`is` as Is

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java,true,true)

    @Test
    fun validTextContent() {
        onView(withText("Hello World")).check(matches(isDisplayed()))
    }

    @Before
    fun sunRise() {
        Intents.init()

    }

    @After
    fun sunSet() {
        Intents.release()
    }

    @Test
    fun validButtonExists_LaunchesToast() {

        onView(withId(R.id.btnHello)).perform(click())

        onView(withText("Clicked")).inRoot(
            withDecorView(
                not(Is(activityRule.activity.window.decorView))
            )
        ).check(matches(isDisplayed()))
    }

    @Test
    fun whenGoActivityButtonClicked_SecondActivityLaunches() {
        onView(withId(R.id.btnIntent)).perform(click())

        intended(hasComponent(SecondActivity::class.java.name))

        onView(withText("Second Activity")).check(matches(isDisplayed()))
    }
    @Test
    fun whenClickPhoneButton_OpenPhone(){
        onView(withId(R.id.btnPhone)).perform(click())
        //intended(allOf(hasAction(Intent.ACTION_DIAL)))
        val stubIntent : Intent = Intent()
        val stubResult = Instrumentation.ActivityResult(Activity.RESULT_OK,stubIntent)
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_CALL)).respondWith(stubResult)
    }
}