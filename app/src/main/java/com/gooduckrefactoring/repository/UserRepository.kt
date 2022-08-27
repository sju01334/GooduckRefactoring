package com.gooduckrefactoring.repository

import android.app.Application
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.dto.BasicResponse
import retrofit2.Call

class UserRepository {

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository? { // singleton pattern
            if (instance == null) instance = UserRepository()
            return instance
        }
    }

    suspend fun getRequestMyInfo() : Call<BasicResponse>{
        return RetrofitInstance.apiList.getRequestMyInfo()
    }

//    suspend fun postRequestLogin() : Call<BasicResponse>{
////        return RetrofitInstance.apiList.postRequestLogin()
//    }


}
