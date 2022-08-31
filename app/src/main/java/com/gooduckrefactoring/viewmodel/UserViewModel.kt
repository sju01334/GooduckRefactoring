package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gooduckrefactoring.repository.remote.UserRepository
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.UserData
import kotlinx.coroutines.launch

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




