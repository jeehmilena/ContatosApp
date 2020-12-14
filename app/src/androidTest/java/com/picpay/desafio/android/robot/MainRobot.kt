package com.picpay.desafio.android.robot

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.PicPayAPI
import com.picpay.desafio.android.R
import com.picpay.desafio.android.utils.MainUtils
import com.picpay.desafio.android.view.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

class MainRobot(
    private val mockWebServer: MockWebServer?,
    private val activityTestRule: ActivityTestRule<MainActivity>
) {

    fun setRequest(): MainRobot {
        mockWebServer?.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.path?.contains(PicPayAPI.SOURCE) == true) {
                    return MockResponse().setBody(
                        readFileFromAssets(
                            "response.json",
                            InstrumentationRegistry.getInstrumentation().context
                        )
                    )
                }

                return MockResponse().setBody(
                    readFileFromAssets(
                        "error_not_found.json",
                        InstrumentationRegistry.getInstrumentation().context
                    )
                )
            }
        }

        return this
    }

    private fun readFileFromAssets(fileName: String, c: Context): String {
        return try {
            val `is`: InputStream = c.assets.open(fileName)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun startActivity(): MainRobot {
        activityTestRule.launchActivity(Intent(Intent.ACTION_MAIN))
        return this
    }

    fun finishActivity() {
        activityTestRule.finishActivity()
    }

    fun checkIsDisplayedRecyclerView() {
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }


    fun checkScroll(): MainRobot{
        MainUtils.scrollToRecyclerViewLastPosition(activityTestRule.activity, R.id.recyclerView)
        MainUtils.scroll(R.id.recyclerView, 8)
        MainUtils.checkTextIsDisplayedOnRecyclerViewPosition(R.id.recyclerView, 11, "Eveline Dantas")

        return this
    }
}