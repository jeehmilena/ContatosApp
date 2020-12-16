package com.picpay.desafio.android.utils

import android.app.Activity
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.picpay.desafio.android.R

object MainUtils {

    fun scroll(recyclerId: Int, pos: Int) {
        onView(withId(recyclerId))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(pos))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatchers {
        return RecyclerViewMatchers(recyclerViewId)
    }

    fun checkTextIsDisplayedOnRecyclerViewPosition(id: Int, position: Int, text: String) {
        onView(withRecyclerView(id).atPosition(position))
            .check(matches(hasDescendant(withText(text))))
    }

    fun checkRecyclerView(recyclerViewId: Int) {
        onView(withId(recyclerViewId))
            .check(matches(isDisplayed()))
    }

    fun scrollToRecyclerViewLastPosition(
        activity: Activity,
        @IdRes recyclerViewId: Int
    ) {
        val recyclerView = activity.findViewById<RecyclerView>(recyclerViewId)
        val itemCount = recyclerView.adapter?.itemCount as Int
        onView(withId(recyclerViewId))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

}