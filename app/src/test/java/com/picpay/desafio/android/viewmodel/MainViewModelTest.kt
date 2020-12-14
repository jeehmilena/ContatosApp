package com.picpay.desafio.android.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.PicPayRepository
import com.picpay.desafio.android.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.Mockito

class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var repository: PicPayRepository

    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        MockKAnnotations.init(this, relaxUnitFun = true)
        val user = getUser()
        coEvery { repository.getUsers() } returns (listOf(user))

        viewModel = MainViewModel(testCoroutineDispatcher, repository)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun testGetListApod() {
        runBlocking {
            viewModel.getListUsers()
        }
        assertEquals("Eduardo Santos", viewModel.stateList.value?.get(0)?.name)
    }

    private fun getUser()= User(
    "teste", "Eduardo Santos", 1, "@eduardo.santos"
    )
}