package com.team13.dealmymeal

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EditMealTest: TestCase() {

    private lateinit var db: DBManager
    private lateinit var mealDao: MealDao

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Before
    public override fun setUp() {
        // get context -- this is an instrumental test
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // init the db and dao variable
        db = Room.databaseBuilder(context, DBManager::class.java, "dmm.db").build()
        mealDao = db.mealDao()
    }

    @Test
    fun openEditMealFragment() = runBlocking {
        val meal = Meal("asdfqwer1234", listOf() ,0f)
        onView(withId(R.id.navigation_addMeal)).perform(click())
        onView(withId(R.id.form_edit))
            .perform(ViewActions.typeText(meal.title))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.form_save)).perform(click())
        Thread.sleep(500)
        onView(withId(R.id.navigation_overview)).perform(click())
        Thread.sleep(500)
        onView(withText(meal.title)).check(matches(isDisplayed()))
        onView(withText(meal.title)).perform(longClick())
        onView(withId(R.id.form_edit)).check(matches(withText(meal.title)))
        assertTrue(true)
    }

    @Test
    fun editMeal() = runBlocking {
        onView(withId(R.id.navigation_overview)).perform(click())
        Thread.sleep(500)
        onView(withText("asdfqwer1234")).check(matches(isDisplayed()))
        onView(withText("asdfqwer1234")).perform(longClick())
        onView(withId(R.id.form_edit)).check(matches(withText("asdfqwer1234")))
        onView(withId(R.id.form_edit)).perform(ViewActions.typeText("asdfqwer1234edit"))
        onView(withText("asdfqwer1234edit")).check(matches(isDisplayed()))
        onView(withId(R.id.form_save)).perform(click())
        Thread.sleep(500)
        pressBack()
        onView(withText("asdfqwer1234edit")).check(matches(isDisplayed()))

        assertTrue(true)

    }

    @After
    fun cleanUp() = runBlocking {
        //mealDao.deleteTestItems()
    }


}