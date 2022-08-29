package com.gooduckrefactoring.repository

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.UserData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository() {

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository? { // singleton pattern
            if (instance == null) instance = UserRepository()
            return instance
        }
    }

    suspend fun getRequestMyInfo(result: (Result<UserData>) -> Unit) {
        RetrofitInstance.apiList.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!.data!!.user))
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
            }
        })
    }

    suspend fun postRequestLogin(email: String, pw: String, result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.postRequestLogin(email, pw).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!))
//                    Log.d("login_code",  response.body()!!.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
                    result(Result.Error(message))
//                    Log.d("login_code", code + message)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
//                result(Result.Error(it))
            }
        })

    }

    suspend fun postRequestSocialLogin(provider: String, uid: String, nick: String, result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.postRequestSocialLogin(provider, uid, nick).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!))
//                    Log.d("login_code",  response.body()!!.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
                    result(Result.Error(message))
//                    Log.d("login_code", code + message)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
//                result(Result.Error(it))
            }
        })

    }





}


