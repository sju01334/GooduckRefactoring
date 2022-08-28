package com.gooduckrefactoring.viewmodel

import android.app.Application
import android.content.Context
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

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val _user = MutableLiveData<UserData>()

    //livedata -> 변경이 불가능한 데이터
    val user: LiveData<UserData> get() = _user

    private val repository by lazy {
        UserRepository.getInstance()
    }

    init {
        viewModelScope.launch {
            repository!!.getRequestMyInfo().enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    if(response.isSuccessful) {
                        val br = response.body()!!
                        ContextUtil.setLoginToken(application, br.data.token)
                        _user.value = br.data.user
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    t.message?.let { Log.d("########", it) }
                }
            })
        }

    }


}




