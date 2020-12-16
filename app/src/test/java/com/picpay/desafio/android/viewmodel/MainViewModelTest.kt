package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.PicPayRepository
import com.picpay.desafio.android.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var repository: PicPayRepository

    lateinit var viewModel: MainViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        MockKAnnotations.init(this, relaxUnitFun = true)
        val user = getUserMock()

        coEvery { repository.getUsers() } returns (listOf(user))

        viewModel = MainViewModel(testCoroutineDispatcher, repository)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun testGetListContatos() {
        runBlocking {
            viewModel.getListUsers()
        }
        assertEquals("Eduardo Santos", viewModel.stateList.value?.get(0)?.name)
    }

    private fun getUserMock()= User(
    "teste", "Eduardo Santos", 1, "@eduardo.santos"
    )
}