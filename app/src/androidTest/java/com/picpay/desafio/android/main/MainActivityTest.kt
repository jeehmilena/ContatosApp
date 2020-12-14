package com.picpay.desafio.android.main

import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.PicPayService
import com.picpay.desafio.android.robot.MainRobot
import com.picpay.desafio.android.view.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private lateinit var robot: MainRobot
    private var mockWebServer: MockWebServer? = null

    init {
        mockWebServer = MockWebServer()
        PicPayService.setBaseUrl(mockWebServer?.url("/").toString())
    }

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setup() {
        robot = MainRobot(mockWebServer, activityRule)
    }

    @After
    fun tearDown() {
        mockWebServer?.shutdown();
        robot.finishActivity()
    }

    @Test
    fun testNavigateListUsers(){
        robot.setRequest()
            .startActivity()
            .checkIsDisplayedRecyclerView()
    }

    @Test
    fun testScrollList() {
        robot.setRequest()
            .startActivity()
            .checkScroll()
    }
}