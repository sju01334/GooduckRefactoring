package com.gooduckrefactoring.data.product

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.Result
import com.gooduckrefactoring.dto.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productDatasouce: ProductDatasouce) {

    suspend fun getRequestBanner(result: (Result<BasicResponse>) -> Unit) {
        productDatasouce.getRequestBanner(result)

    }
    suspend fun getRequestAllProduct(result: (Result<BasicResponse>) -> Unit) {
      productDatasouce.getRequestAllProduct(result)

    }

    suspend fun getRequestProducts(id : Int , result: (Result<BasicResponse>) -> Unit) {
        productDatasouce.getRequestProducts(id, result)

    }






}


