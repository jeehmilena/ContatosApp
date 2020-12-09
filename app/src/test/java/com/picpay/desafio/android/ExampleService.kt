package com.picpay.desafio.android

import com.picpay.desafio.android.model.User

class ExampleService(
    private val API: PicPayAPI
) {

    fun example(): List<User> {
        val users = API.getUsers().execute()

        return users.body() ?: emptyList()
    }
}