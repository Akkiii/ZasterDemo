package com.zasterapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.zasterapp.initial.InitialActivity
import org.junit.Rule
import java.util.regex.Pattern.matches


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    val USERNAME_TYPED = "+919913157299"
    val PASSWORD = "1234567"

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.zasterapp", appContext.packageName)
    }

    @Rule
    var mActivityRule = ActivityTestRule<InitialActivity>(
        InitialActivity::class.java
    )

    @Test
    fun loginClickedSuccess() {
        onView(withId(R.id.editTextEmailAddress))
            .perform(typeText(USERNAME_TYPED))
        onView(withId(R.id.editTextPassword))
            .perform(typeText(PASSWORD))

        onView(withId(R.id.buttonLogin)).perform(click())
    }
}
