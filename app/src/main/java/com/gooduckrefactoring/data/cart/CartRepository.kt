package com.gooduckrefactoring.data.cart

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.Result
import com.gooduckrefactoring.dto.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository(private val cartDatasource: CartDatasource) {

    suspend fun getRequestMyCartList(result: (Result<BasicResponse>) -> Unit) {
        cartDatasource.getRequestMyCartList(result)
    }

    suspend fun postRequestAddCart(id : Int , result: (Result<BasicResponse>) -> Unit) {
        cartDatasource.postRequestAddCart(id, result)
    }






}


