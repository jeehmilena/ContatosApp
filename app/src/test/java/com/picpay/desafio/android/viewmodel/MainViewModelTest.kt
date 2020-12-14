package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.PicPayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private var repository = Mockito.mock(PicPayRepository::class.java)

    @Before
    fun up() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun testGetListApod() {
        testCoroutineDispatcher.runBlockingTest {
            val viewModel = MainViewModel(testCoroutineDispatcher, repository)
            viewModel.getListUsers()
            assertEquals(
                30, viewModel.stateList.value?.size
            )
        }
    }
}