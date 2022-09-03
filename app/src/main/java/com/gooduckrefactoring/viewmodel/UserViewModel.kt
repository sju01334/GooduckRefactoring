package com.gooduckrefactoring.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.user.UserRepository
import com.gooduckrefactoring.data.Result
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application,
    repository : UserRepository
) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserData>()

    //livedata -> 변경이 불가능한 데이터
    val user: LiveData<UserData> get() = _user



    init {
        RetrofitInstance.token = ContextUtil.getLoginToken(application)
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




