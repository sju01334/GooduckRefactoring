package com.gooduckrefactoring.repository.category

import android.util.Log
import com.gooduckrefactoring.network.RetrofitInstance
import com.gooduckrefactoring.repository.Result
import com.gooduckrefactoring.dto.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDatasouce {

    suspend fun getRequestAllCategory(result: (Result<BasicResponse>) -> Unit){
        RetrofitInstance.apiList.getRequestAllCategory().enqueue(object : Callback<BasicResponse> {
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

    fun getRequestProducts(id : Int, result: (Result<BasicResponse>) -> Unit){
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