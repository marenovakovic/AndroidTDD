package com.example.androidtdd

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.androidtdd.users.ui.UsersAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
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
        onView(withId(R.id.usersRecyclerView))
            .check(matches(RecyclerViewMatchers.hasItemCount(10)))
    }

    @Test
    fun shouldShowOnlySearchedUsersAfterSearchTextIsEntered() {
        onView(withId(R.id.userSearch)).perform(typeText("Ervin"))
        onView(withId(R.id.usersRecyclerView))
            .check(matches(RecyclerViewMatchers.hasItemCount(1)))
    }

    @Test
    fun onUserTapShouldNavigateToDetailsScreen() {
        onView(withId(R.id.usersRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.UserHolder>(
                    1,
                    click(),
                ),
            )
        onView(withId(R.id.userDetailsName)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowDetailsForClickedUser() {
        val name = "Ervin"
        onView(withId(R.id.usersRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItem<UsersAdapter.UserHolder>(
                    withText(name),
                    click(),
                ),
            )
        onView(withId(R.id.userDetailsName)).check(matches(withText(name)))
    }

    object RecyclerViewMatchers {
        fun hasItemCount(count: Int): Matcher<View> =
            object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
                override fun matchesSafely(item: RecyclerView?): Boolean =
                    item?.adapter?.itemCount == count

                override fun describeTo(description: Description?) {
                    description?.appendText("RecyclerView with item count: $count")
                }
            }
    }
}