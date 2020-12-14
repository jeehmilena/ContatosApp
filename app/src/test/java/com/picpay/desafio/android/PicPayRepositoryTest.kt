package com.picpay.desafio.android

import com.picpay.desafio.android.model.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PicPayRepositoryTest {

    private lateinit var repository: PicPayRepository

    @MockK
    lateinit var user: User

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { user.name } returns "Eduardo Santos"

        repository = PicPayRepository()

    }

    @Test
    fun getListTest() {
        runBlocking {
            repository.getUsers()
        }
        assertEquals("Eduardo Santos", user.name)
    }
}