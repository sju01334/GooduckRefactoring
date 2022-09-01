package com.gooduckrefactoring.repository.remote

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.dto.BasicResponse
import com.gooduckrefactoring.util.Result
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository() {

    companion object {
        private var instance: CartRepository? = null

        fun getInstance(): CartRepository? { // singleton pattern
            if (instance == null) {
                instance = CartRepository()
            }
            return instance
        }
    }

    suspend fun getRequestMyCartList(result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.getRequestMyCartList().enqueue(object : Callback<BasicResponse> {
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

    suspend fun postRequestAddCart(id : Int , result: (Result<BasicResponse>) -> Unit) {
        RetrofitInstance.apiList.postRequestAddCart(id).enqueue(object : Callback<BasicResponse> {
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


