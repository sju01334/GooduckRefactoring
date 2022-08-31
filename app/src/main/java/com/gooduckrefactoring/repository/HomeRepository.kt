package com.gooduckrefactoring.repository

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.util.Result
import com.nepplus.gooduck.models.UserData
import com.nepplus.gooduck.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository() {

    companion object {
        private var instance: HomeRepository? = null

        fun getInstance(): HomeRepository? { // singleton pattern
            if (instance == null) {
                instance = HomeRepository()
            }
            return instance
        }
    }

    suspend fun getRequestBanner(result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.getRequestBanner().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!))
//                    Log.d("login_code",  response.body()!!.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
//                    result(Result.Error(message))
                    Log.d("login_code", code + message)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
//                result(Result.Error(it))
            }
        })

    }
    suspend fun getRequestAllProduct(result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.getRequestAllProduct().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!))
//                    Log.d("login_code",  response.body()!!.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
//                    result(Result.Error(message))
                    Log.d("login_code", code + message)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
            }
        })

    }

    suspend fun getRequestProducts(id : Int , result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.getRequestProducts(id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    result(Result.Success(response.body()!!))
//                    Log.d("login_code",  response.body()!!.toString())
                } else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val message = jsonObj.getString("message")
                    val code = jsonObj.getString("code")
//                    result(Result.Error(message))
                    Log.d("login_code", code + message)
                }
            }
            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                t.message?.let { Log.d("########", it) }
            }
        })

    }






}


