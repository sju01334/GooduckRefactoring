package com.gooduckrefactoring.data.category

import android.util.Log
import com.gooduckrefactoring.api.RetrofitInstance
import com.gooduckrefactoring.data.Result
import com.gooduckrefactoring.dto.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRepository(private val categoryDatasouce: CategoryDatasouce) {

    suspend fun getRequestAllCategory(result: (Result<BasicResponse>) -> Unit) {
      categoryDatasouce.getRequestAllCategory(result)
    }

    suspend fun getRequestProducts(id : Int , result: (Result<BasicResponse>) -> Unit) {
       categoryDatasouce.getRequestProducts(id, result)
    }






}


