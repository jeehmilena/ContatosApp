package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.PicPayRepository
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val repository = PicPayRepository()
    var stateList: MutableLiveData<List<User>> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getListUsers() {
        viewModelScope.launch {
            loading.value = true
            try {
                val usersResult = withContext(Dispatchers.IO) {
                    repository.getUsers()
                }
                stateList.value = usersResult
                loading.value = false
            } catch (ex: Exception) {
                errorMessage("It looks like we had a problem. Try later!")
            } finally {
                loading.value = false
            }
        }
    }

    private fun errorMessage(message: String) {
        error.value = message
    }
}