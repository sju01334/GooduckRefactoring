package com.gooduckrefactoring.data.user

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.Result
import com.gooduckrefactoring.dto.BasicResponse
import com.nepplus.gooduck.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDatasource {

    fun getRequestMyInfo(result: (Result<UserData>) -> Unit) {
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
}