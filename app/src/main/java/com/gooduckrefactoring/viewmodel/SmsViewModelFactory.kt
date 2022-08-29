package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gooduckrefactoring.repository.UserRepository

class SmsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmsViewModel::class.java)) {
            return SmsViewModel(application) as T
        }
        throw IllegalArgumentException("Not found ViewModel class.")
    }
}