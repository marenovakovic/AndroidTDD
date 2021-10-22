package com.example.androidtdd

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.androidtdd.users.models.User
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UsersFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun shouldShowUserSearch() {
        onView(withId(R.id.userSearch)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayUsersRecyclerView() {
        onView(withId(R.id.usersRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayAllUsersOnStart() {
        onData(allOf(`is`(instanceOf(User::class.java)), hasChildCount(10)))
    }

    @Test
    fun shouldShowSearchedUsersAfterSearchTextIsEntered() {
        onView(withId(R.id.userSearch)).perform(typeText("Ervin"))
        onData(allOf(`is`(instanceOf(User::class.java)), hasChildCount(6)))
    }
}