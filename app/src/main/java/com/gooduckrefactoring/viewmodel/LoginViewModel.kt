package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.text.Editable
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.dto.DataResponse
import com.gooduckrefactoring.repository.UserRepository
import com.gooduckrefactoring.util.Event
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _navigateToDetails = MutableLiveData<Event<Boolean>>()

    val navigateToDetails : LiveData<Event<Boolean>>
        get() = _navigateToDetails



    private var _isEmailError = MutableLiveData<Boolean>()
    private var _isPWError = MutableLiveData<Boolean>()
    private var _response = MutableLiveData<BasicResponse>()
    private var _errorMessage = MutableLiveData<String>()


    val isEmailError: LiveData<Boolean> get() = _isEmailError
    val isPWError: LiveData<Boolean> get() = _isPWError
    val response: LiveData<BasicResponse> get() = _response
    val errorMessage: LiveData<String> get() = _errorMessage

    private val repository by lazy {
        UserRepository.getInstance()
    }

    init {
        _isEmailError.value = false
        _isPWError.value = false
        _errorMessage.value = "알맞는 정보를 입력해주세요"
    }

    fun isEmailValid(editable: Editable) {
        _isEmailError.value = !Patterns.EMAIL_ADDRESS.matcher(editable).matches()
        Log.d("12345", _isEmailError.value.toString())
    }

    fun isPasswordValid(editable: Editable) {
        _isPWError.value = editable.length < 5
        Log.d("12345#", _isPWError.value.toString())
    }

    fun normalLogin(email: String, pw: String) {

        Log.d("로그인", email+pw)

        if (_isEmailError.value == false && _isPWError.value == false) {
            viewModelScope.launch {
                repository!!.postRequestLogin(email = email, pw = pw){
                    if(it is Result.Success){
                        _response.value = it.data
                        ContextUtil.setLoginToken(getApplication(), it.data.data!!.token)
//                        Log.d("로그인정보", it.data.data.toString())
                    }else if(it is Result.Error){
                        _errorMessage.value = it.exception
                    }
                }
            }
        } else {
            _errorMessage.value = "알맞는 정보를 입력해주세요"
        }


    }

    fun kakaoLogin() {

    }

    fun naverLogin() {

    }

    fun userClicksOnButton(itemId: Boolean, email : String, pw : String) {
//        normalLogin(email, pw)
        _navigateToDetails.value = Event(itemId)
    }

}




