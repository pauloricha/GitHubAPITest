package br.com.example.githubapitest.view

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import br.com.example.githubapitest.R
import org.junit.Rule
import org.junit.Test

class RepositoriesActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<RepositoriesActivity>(
        RepositoriesActivity::class.java
    )

    @Test
    fun clickRecyclerView() {
        onView(withId(R.id.rvRepositories)).perform(swipeUp(), RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()));
    }
}