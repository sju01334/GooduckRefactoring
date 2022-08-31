package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.repository.remote.UserRepository
import java.util.regex.Pattern


class SmsViewModel(application: Application) : AndroidViewModel(application) {
//    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()
//    val navigateToDetails : LiveData<Event<Boolean>>
//        get() = _navigateToDetails


    private var _isPhoneError = MutableLiveData<Boolean>()
    private var _isAuthError = MutableLiveData<Boolean>()
    private var _response = MutableLiveData<BasicResponse>()
    private var _errorMessage = MutableLiveData<String>()


    val isPhoneError: LiveData<Boolean> get() = _isPhoneError
    val isAuthError: LiveData<Boolean> get() = _isAuthError
    val response: LiveData<BasicResponse> get() = _response
    val errorMessage: LiveData<String> get() = _errorMessage

    private val repository by lazy {
        UserRepository.getInstance()
    }

    init {
        _isPhoneError.value = false
        _isAuthError.value = false
        _errorMessage.value = ""
    }

    fun isPhoneValid(editable: Editable) {
        val regexStr = "^[0-9]$"
        _isPhoneError.value = !Pattern.compile(regexStr).matcher(editable).matches();
        Log.d("폰넘버",  _isPhoneError.value.toString())
    }

    fun isAuthValid(editable: Editable) {
        _isAuthError.value = editable.length < 5
    }


//    fun userClicksOnButton(itemId: Boolean, email : String, pw : String) {
////        normalLogin(email, pw)
//        _navigateToDetails.value = Event(itemId)
//    }

}






