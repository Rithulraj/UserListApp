package com.bridge.global.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridge.global.room.UserRepository

class MainViewModelFactory constructor(private val repository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}