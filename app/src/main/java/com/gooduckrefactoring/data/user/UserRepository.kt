package com.gooduckrefactoring.data.user

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.Result
import com.gooduckrefactoring.dto.BasicResponse
import com.nepplus.gooduck.models.UserData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val userDatasource : UserDatasource) {

    suspend fun getRequestMyInfo(result: (Result<UserData>) -> Unit) {
        userDatasource.getRequestMyInfo(result)
    }

    suspend fun postRequestLogin(email: String, pw: String, result: (Result<BasicResponse>) -> Unit) {
        userDatasource.postRequestLogin(email, pw, result)
    }

    suspend fun postRequestSocialLogin(provider: String, uid: String, nick: String, result: (Result<BasicResponse>) -> Unit) {
        userDatasource.postRequestSocialLogin(provider, uid, nick, result)

    }





}


