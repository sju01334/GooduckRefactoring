package com.gooduckrefactoring.repository

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.dto.DataResponse
import com.nepplus.gooduck.models.UserData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository? { // singleton pattern
            if (instance == null) instance = UserRepository()
            return instance
        }
    }

    suspend fun getRequestMyInfo(): UserData? {
        var user: UserData? = null
        RetrofitInstance.apiList.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    user = br.data!!.user
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
            }
        })
        return user
    }

    suspend fun postRequestLogin(email: String, pw: String): BasicResponse {
        var data: BasicResponse? = null
        RetrofitInstance.apiList.postRequestLogin(email, pw).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    data = response.body()!!
                    Log.d("login_code",  data.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
                    data = BasicResponse(code.toInt(), message, null)
                    Log.d("login_code", code + message)

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
            }
        })

        return data!!
    }

}


