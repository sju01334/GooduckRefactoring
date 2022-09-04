package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gooduckrefactoring.network.RetrofitInstance
import com.gooduckrefactoring.repository.user.UserRepository
import com.gooduckrefactoring.repository.Result
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application,
    private val repository : UserRepository
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> get() = _user


    init {
        RetrofitInstance.token = ContextUtil.getLoginToken(application)
        viewModelScope.launch {
            repository.getRequestMyInfo {
                if (it is Result.Success) {
                    _user.value = it.data
                } else {
                }
            }
        }
    }


}




