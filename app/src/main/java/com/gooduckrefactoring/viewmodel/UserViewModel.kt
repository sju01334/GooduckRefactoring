package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.repository.UserRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserData>()

    //livedata -> 변경이 불가능한 데이터
    val user: LiveData<UserData> get() = _user

    private val repository by lazy {
        UserRepository.getInstance()
    }

    init {
        viewModelScope.launch {
            repository!!.getRequestMyInfo() {
                if (it is Result.Success) {
                    _user.value = it.data
                } else {
                }
            }
        }
    }


}



