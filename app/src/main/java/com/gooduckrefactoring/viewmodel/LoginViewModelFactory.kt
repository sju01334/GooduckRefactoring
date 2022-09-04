package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.data.user.UserDatasource
import com.gooduckrefactoring.data.user.UserRepository

class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application, repository = UserRepository(UserDatasource())) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}