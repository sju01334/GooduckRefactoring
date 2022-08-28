package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.content.Context
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.repository.UserRepository
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private var _isEmailError = MutableLiveData<Boolean>()
    private var _isPWError = MutableLiveData<Boolean>()

    val isEmailError: LiveData<Boolean> get() = _isEmailError
    val isPWError: LiveData<Boolean> get() = _isPWError

    private val repository by lazy {
        UserRepository.getInstance()
    }

    init {
        _isEmailError.value = false
        _isPWError.value = false
    }

    fun isEmailValid(editable: Editable) {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(editable).matches()
        Log.d("12345", _isEmailError.value.toString())
    }

    fun isPasswordValid(editable: Editable) {
        _isPWError.value =  editable.length < 5
        Log.d("12345#", _isPWError.value.toString())
    }

}




